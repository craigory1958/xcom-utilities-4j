

package xcom.utils4j.format ;


import static java.util.regex.Pattern.DOTALL ;
import static java.util.regex.Pattern.MULTILINE ;

import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.Map ;
import java.util.Map.Entry ;
import java.util.regex.Matcher ;
import java.util.regex.Pattern ;

import org.apache.commons.lang3.StringUtils ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


public abstract class Templates {

	/**
	 * Replaces named tags within a template with values from a <code>Map</code>.
	 *
	 * <pre>
	 * "xyzzy <@key1@> xyzzy <@key2@> xyzzy" >>> "xyzzy value1 xyzzy value2 xyzzy"
	 * </pre>
	 *
	 * <ul>
	 * <li>The replacement value from the <code>Map</code> it determined by the key value delimited by OpenTagDelimiter and CloseTagDelimiter (
	 * <code>tagDelimiters[0]</code>and <code>tagDelimiters[1]</code> respectively.) The delimiters and enclosing key value are replaced.</li>
	 * <li>If the key is not contained in the <code>Map</code>, the delimiters and enclosing key value are removed from the template.</li>
	 * <li>Delimiters may be 1 or more characters in length.</li>
	 * </ul>
	 *
	 * @param  template
	 *                           - Template containing tags to be replaced.
	 * @param  tagDelimiters
	 *                           - An array containing the OpenTagDelimiter and CloseTagDelimiter.
	 * @param  values
	 *                           - Values to be injected into the template.
	 * @return               Template with tags replaced with values.
	 */
	@Log
	public static StringBuilder replaceTags(final StringBuilder template, final String[] tagDelimiters, final Map<String, Object> values) {

		if ( (tagDelimiters == null) || (StringUtils.trimToNull(tagDelimiters[0]) == null) || (StringUtils.trimToNull(tagDelimiters[1]) == null) )
			throw new IllegalArgumentException("Tag delimiters cannot be 'null' or blank.") ;


		StringBuilder template_ = new StringBuilder(template) ;
		StringBuilder results = null ;

		Map<String, Object> _values = new HashMap<>() ;
		if ( values != null )
			_values = values ;


		final String regEx = ".*?" + Patterns.escapeMetaCharacters(tagDelimiters[0]) + "(.*?)" + Patterns.escapeMetaCharacters(tagDelimiters[1]) ;

		boolean found = true ;
		while ( found ) {
			found = false ;
			results = new StringBuilder(template_) ;

			int offset = 0 ;
			final Matcher matcher = Pattern.compile(regEx).matcher(template_) ;
			while ( matcher.find() ) {
				found = true ;
				final int beg = matcher.start(1) - tagDelimiters[0].length() ;
				final int end = matcher.end(1) + tagDelimiters[1].length() ;
				final String key = matcher.group(1) ;

				final String replacement = (_values.get(key) != null ? _values.get(key).toString() : "") ;

				results.replace(beg + offset, end + offset, replacement) ;
				offset += replacement.length() - (end - beg) ;
			}
			
			template_ = new StringBuilder(results) ;
		}

		return results ;
	}


	/**
	 * Replaces repeating elements within a template with values from a <code>List</code> of <code>Map</code>s.
	 *
	 * <pre>
	 * "before repeating <%.<@key1@> xyzzy <@key2@>.%> after repeating" >>> "before repeating .value11 xyzzy value12..value21 xyzzy value22. after repeating"
	 * </pre>
	 *
	 * <br/>
	 * <br/>
	 * The size of the list determines the number of times a tag is repeated. The repeating tag is processed once for each item in the values list. During each
	 * repeat, the corresponding <code>Map</code> from the list of values is used for value replacement.
	 *
	 * @param  template
	 *                                 - Template containing tags to be replaced.
	 * @param  tagDelimiters
	 * @param  repeatingDelimiters
	 * @param  repeatValues
	 *                                 - A list of values sets.
	 * @return                     Template with tags replaced with values.
	 */
	@Log
	public static StringBuilder replaceRepeatedTags(final StringBuilder template, final String[] tagDelimiters, final String[] repeatingDelimiters,
			final ArrayList<Map<String, Object>> repeatValues) {

		final StringBuilder results = new StringBuilder(template) ;

		int replacedOffset = 0 ;
		final String repeatRegEx =
				".*?" + Patterns.escapeMetaCharacters(repeatingDelimiters[0]) + "(.*?)" + Patterns.escapeMetaCharacters(repeatingDelimiters[1]) ;

		final Matcher repeatMatcher = Pattern.compile(repeatRegEx, DOTALL | MULTILINE).matcher(template) ;
		while ( repeatMatcher.find() ) {
			final int repeatBeg = repeatMatcher.start(1) - repeatingDelimiters[0].length() ;
			final int repeatEnd = repeatMatcher.end(1) + repeatingDelimiters[1].length() ;
			String _repeats = "" ;
			int currentRow = 0 ;

			for ( final Map<String, Object> tags : repeatValues ) {
				final StringBuilder _repeat =
						new StringBuilder(template.substring(repeatBeg + repeatingDelimiters[0].length(), repeatEnd - repeatingDelimiters[1].length())) ;

				if ( tags != null )
					for ( final Entry<String, Object> tag : tags.entrySet() ) {
						final String token = tagDelimiters[0] + tag.getKey() + tagDelimiters[1] ;

						int pos = 0 ;
						while ( (pos = _repeat.indexOf(token, pos)) != -1 )
							_repeat.replace(pos, pos + token.length(), ((tag.getValue() != null) ? tag.getValue().toString() : "")) ;
					}

				final StringBuilder _exclude = new StringBuilder(_repeat) ;
				int excludeOffset = 0 ;
				final String excludeRegEx = ".*?" + repeatingDelimiters[2] + "(.+?):(.*?)" + repeatingDelimiters[3] ;

				final Matcher excludeMatcher = Pattern.compile(excludeRegEx, DOTALL | MULTILINE).matcher(_repeat) ;
				while ( excludeMatcher.find() ) {
					final int excludedRow = Integer.valueOf(_repeat.substring(excludeMatcher.start(1), excludeMatcher.end(1))) ;
					final int excludeBeg = excludeMatcher.start(1) - repeatingDelimiters[2].length() ;
					final int excludeEnd = excludeMatcher.end(2) + repeatingDelimiters[3].length() ;
					String exclude = new StringBuilder(_repeat.substring(excludeMatcher.start(2), excludeEnd - repeatingDelimiters[3].length())).toString() ;

					if ( (excludedRow == 0) && (currentRow == 0) )
						exclude = "" ; // Exclude 1st occurrence.

					if ( (excludedRow > 0) && ((currentRow % excludedRow) == 0) )
						exclude = "" ; // Exclude the nth occurrence.

					_exclude.replace(excludeBeg + excludeOffset, excludeEnd + excludeOffset, exclude) ;
					excludeOffset += exclude.length() - (excludeEnd - excludeBeg) ;
				}

				_repeats += _exclude ;
				currentRow++ ;
			}

			results.replace(repeatBeg + replacedOffset, repeatEnd + replacedOffset, _repeats) ;
			replacedOffset += _repeats.length() - (repeatEnd - repeatBeg) ;
		}

		return results ;
	}
}
