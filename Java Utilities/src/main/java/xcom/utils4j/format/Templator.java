

package xcom.utils4j.format ;


import java.util.ArrayList ;
import java.util.HashMap ;
import java.util.List ;
import java.util.Map ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


/**
 * A fluent API for injecting values into a template.
 *
 * <pre>
 * StringBuilder = Templator
 *
 *     [
 *     	   .delimiters(OpenTagDelimiter, CloseTagDelimiter, OpenRepeatingDelimiter, CloseRepeatingDelimiter, OpenRepeatingExcludedDelimiter, CloseRepeatingExcludedDelimiter)
 *
 *       | .tagDelimiters(OpenTagDelimiter, CloseTagDelimiter)
 *
 *       | .repeatingDelimiters(OpenRepeatingDelimiter, CloseRepeatingDelimiter, OpenRepeatingExcludedDelimiter, CloseRepeatingExcludedDelimiter)
 *
 *       | .openTagDelimiter(String) | .closeTagDelimiter(String)
 *
 *       | .openRepeatingDelimiter(String) | .closeRepeatingDelimiter(String)
 *
 *       | .openRepeatingExcludedDelimiter(String) | .closeRepeatingExcludedDelimiter(String)
 *     ]
 *
 *     .template(StringBuilder)
 *
 *     [
 *         .inject( value, value, ... | value[] | List | Map )
 *       | .injectRepeating( List | Map )
 *     ]
 * </pre>
 *
 */
public abstract class Templator {

	public static final Boolean InjectFromArray = true ;
	public static final Boolean InjectFromVarArgs = true ;

	public static final String DefaultOpenTagDelimiter = "<@" ;
	public static final String DefaultCloseTagDelimiter = "@>" ;

	public static final String DefaultOpenRepeatingDelimiter = "<%" ;
	public static final String DefaultCloseRepeatingDelimiter = "%>" ;
	public static final String DefaultOpenRepeatingExcludedDelimiter = "<!" ;
	public static final String DefaultCloseRepeatingExcludedDelimiter = "!>" ;

	public static final String[] DefaultDelimiters = { DefaultOpenTagDelimiter, DefaultCloseTagDelimiter, DefaultOpenRepeatingDelimiter,
			DefaultCloseRepeatingDelimiter, DefaultOpenRepeatingExcludedDelimiter, DefaultCloseRepeatingExcludedDelimiter } ;
	public static final String[] UnixDelimiters = { "${", "}", "%{", "}", "!{", "}" } ;


	abstract class Templator$Templated {

		/**
		 * The markup delimiters object used during injection.
		 */
		Templator$Delimited delimited ;


		/**
		 * Instantiate a Templated object with the formatting template and markup delimiters.
		 *
		 * @param delimited
		 *            - The markup delimiters.
		 * @param template
		 *            - The formatting template.
		 */
		@Log
		public Templator$Templated(final Templator$Delimited delimited) {
			this.delimited = delimited ;
		}


		public String openTagDelimiter() {
			return delimited.openTagDelimiter ;
		}

		public String closeTagDelimiter() {
			return delimited.closeTagDelimiter ;
		}

		public String openRepeatingDelimiter() {
			return delimited.openRepeatingDelimiter ;
		}

		public String closeRepeatingDelimiter() {
			return delimited.closeRepeatingDelimiter ;
		}

		public String openRepeatingExcludedDelimiter() {
			return delimited.openRepeatingExcludedDelimiter ;
		}

		public String closeRepeatingExcludedDelimiter() {
			return delimited.closeRepeatingExcludedDelimiter ;
		}

		public String[] delimiters() {
			return new String[] { delimited.openTagDelimiter, delimited.closeTagDelimiter, delimited.openRepeatingDelimiter, delimited.closeRepeatingDelimiter,
					delimited.openRepeatingExcludedDelimiter, delimited.closeRepeatingExcludedDelimiter } ;
		}

		public String[] tagDelimiters() {
			return new String[] { delimited.openTagDelimiter, delimited.closeTagDelimiter } ;
		}

		public String[] repeatingDelimiters() {
			return new String[] { delimited.openRepeatingDelimiter, delimited.closeRepeatingDelimiter, delimited.openRepeatingExcludedDelimiter,
					delimited.closeRepeatingExcludedDelimiter } ;
		}


		/**
		 * @param values
		 * @return
		 */
		@Log
		Map<String, Object> getValuesMapFromArray(final Object[] values) {

			final Map<String, Object> _values = new HashMap<>() ;
			for ( Integer i = 0; (i < values.length); i = i + 2 )
				_values.put((String) values[i], values[i + 1]) ;

			return _values ;
		}


		/**
		 * @param values
		 * @return
		 */
		@Log
		Map<String, Object> getValuesMapFromList(final List<Object> values) {

			final Map<String, Object> _values = new HashMap<>() ;
			for ( Integer i = 1; (i <= values.size()); i++ )
				_values.put(i.toString(), values.get(i - 1)) ;

			return _values ;
		}
	}

	public class Templator$TemplatedAsStringBuilder extends Templator$Templated {

		/**
		 * The formatting template.
		 */
		StringBuilder template ;


		/**
		 * Instantiate a Templated object with the formatting template and markup delimiters.
		 *
		 * @param delimited
		 *            - The markup delimiters.
		 * @param template
		 *            - The formatting template.
		 */
		@Log
		public Templator$TemplatedAsStringBuilder(final Templator$Delimited delimited, final StringBuilder template) {
			super(delimited) ;
			this.template = template ;
		}


		/**
		 * Inject a values <code>Array</code> into the formatting template.
		 *
		 * @param values
		 *            - The values to be injected.
		 * @return
		 */
		@Log
		public StringBuilder inject(final boolean asArray, final Object... values) {
			return Templates.replaceTags(template, delimited.delimiters(), getValuesMapFromArray(values)) ;
		}


		/**
		 * Inject a values <code>List</code> into the formatting template.
		 *
		 * @param values
		 * @return
		 */
		@Log
		public StringBuilder inject(final List<Object> values) {
			return Templates.replaceTags(template, delimited.delimiters(), getValuesMapFromList(values)) ;
		}


		/**
		 * Inject a values <code>Map</code> into the formatting template.
		 *
		 * @param values
		 * @return
		 */
		@Log
		public StringBuilder inject(final Map<String, Object> values) {
			return Templates.replaceTags(template, delimited.delimiters(), values) ;
		}


		@Log
		public StringBuilder injectRepeating(final ArrayList<Map<String, Object>> repeatValues) {
			return Templates.replaceRepeatedTags(template, delimited.tagDelimiters(), delimited.repeatingDelimiters(), repeatValues) ;
		}
	}

	public class Templator$TemplatedAsString extends Templator$Templated {

		/**
		 * The formatting template.
		 */
		String template ;


		/**
		 * Instantiate a Templated object with the formatting template and markup delimiters.
		 *
		 * @param delimited
		 *            - The markup delimiters.
		 * @param template
		 *            - The formatting template.
		 */
		@Log
		public Templator$TemplatedAsString(final Templator$Delimited delimited, final String template) {
			super(delimited) ;
			this.template = template ;
		}


		/**
		 * Inject a values <code>Array</code> into the formatting template.
		 *
		 * @param values
		 *            - The values to be injected.
		 * @return
		 */
		@Log
		public String inject(final boolean asVarArgs, final Object... values) {
			return Templates.replaceTags(new StringBuilder(template), delimited.delimiters(), getValuesMapFromArray(values)).toString() ;
		}


		/**
		 * Inject a values <code>List</code> into the formatting template.
		 *
		 * @param values
		 * @return
		 */
		@Log
		public String inject(final List<Object> values) {
			return Templates.replaceTags(new StringBuilder(template), delimited.delimiters(), getValuesMapFromList(values)).toString() ;
		}


		/**
		 * Inject a values <code>Map</code> into the formatting template.
		 *
		 * @param values
		 * @return
		 */
		@Log
		public String inject(final Map<String, Object> values) {
			return Templates.replaceTags(new StringBuilder(template), delimited.delimiters(), values).toString() ;
		}


		@Log
		public String injectRepeating(final ArrayList<Map<String, Object>> repeatValues) {
			return Templates.replaceRepeatedTags(new StringBuilder(template), delimited.tagDelimiters(), delimited.repeatingDelimiters(), repeatValues)
					.toString() ;
		}


		@Override
		public String toString() {
			return template ;
		}
	}


	public class Templator$Delimited {

		/**
		 * The markup delimiting the beginning of characters to be injected.
		 */
		String openTagDelimiter = DefaultOpenTagDelimiter ;

		public String openTagDelimiter() {
			return openTagDelimiter ;
		}

		public Templator$Delimited openTagDelimiter(final String openTagDelimiter) {
			this.openTagDelimiter = openTagDelimiter ;
			return this ;
		}


		/**
		 * The markup delimiting the ending of characters to be injected.
		 */
		String closeTagDelimiter = DefaultCloseTagDelimiter ;

		public String closeTagDelimiter() {
			return closeTagDelimiter ;
		}

		public Templator$Delimited closeTagDelimiter(final String closeTagDelimiter) {
			this.closeTagDelimiter = closeTagDelimiter ;
			return this ;
		}


		/**
		 *
		 */
		String openRepeatingDelimiter = DefaultOpenRepeatingDelimiter ;

		public String openRepeatingDelimiter() {
			return openRepeatingDelimiter ;
		}

		public Templator$Delimited openRepeatingDelimiter(final String openRepeatingDelimiter) {
			this.openRepeatingDelimiter = openRepeatingDelimiter ;
			return this ;
		}


		/**
		 *
		 */
		String closeRepeatingDelimiter = DefaultCloseRepeatingDelimiter ;

		public String closeRepeatingDelimiter() {
			return closeRepeatingDelimiter ;
		}

		public Templator$Delimited closeRepeatingDelimiter(final String closeRepeatingDelimiter) {
			this.closeRepeatingDelimiter = closeRepeatingDelimiter ;
			return this ;
		}


		/**
		 *
		 */
		String openRepeatingExcludedDelimiter = DefaultOpenRepeatingExcludedDelimiter ;

		public String openRepeatingExcludedDelimiter() {
			return openRepeatingExcludedDelimiter ;
		}

		public Templator$Delimited openRepeatingExcludedDelimiter(final String openRepeatingExcludedDelimiter) {
			this.openRepeatingExcludedDelimiter = openRepeatingExcludedDelimiter ;
			return this ;
		}


		/**
		 *
		 */
		String closeRepeatingExcludedDelimiter = DefaultCloseRepeatingExcludedDelimiter ;

		public String closeRepeatingExcludedDelimiter() {
			return closeRepeatingExcludedDelimiter ;
		}

		public Templator$Delimited closeRepeatingExcludedDelimiter(final String closeRepeatingExcludedDelimiter) {
			this.closeRepeatingExcludedDelimiter = closeRepeatingExcludedDelimiter ;
			return this ;
		}


		/**
		 * @return
		 */
		public String[] delimiters() {
			return new String[] { openTagDelimiter, closeTagDelimiter, openRepeatingDelimiter, closeRepeatingDelimiter, openRepeatingExcludedDelimiter,
					closeRepeatingExcludedDelimiter } ;
		}

		public Templator$Delimited delimiters(final String openTagDelimiter, final String closeTagDelimiter, final String openRepeatingDelimiter,
				final String closeRepeatingDelimiter, final String openRepeatingExcludedDelimiter, final String closeRepeatingExcludedDelimiter) {

			this.openTagDelimiter = openTagDelimiter ;
			this.closeTagDelimiter = closeTagDelimiter ;
			this.openRepeatingDelimiter = openRepeatingDelimiter ;
			this.closeRepeatingDelimiter = closeRepeatingDelimiter ;
			this.openRepeatingExcludedDelimiter = openRepeatingExcludedDelimiter ;
			this.closeRepeatingExcludedDelimiter = closeRepeatingExcludedDelimiter ;
			return this ;
		}


		/**
		 * @return
		 */
		public String[] tagDelimiters() {
			return new String[] { openTagDelimiter, closeTagDelimiter } ;
		}

		public Templator$Delimited tagDelimiters(final String openTagDelimiter, final String closeTagDelimiter) {
			this.openTagDelimiter = openTagDelimiter ;
			this.closeTagDelimiter = closeTagDelimiter ;
			return this ;
		}


		/**
		 * @return
		 */
		public String[] repeatingDelimiters() {
			return new String[] { openRepeatingDelimiter, closeRepeatingDelimiter, openRepeatingExcludedDelimiter, closeRepeatingExcludedDelimiter } ;
		}

		public Templator$Delimited repeatingDelimiters(final String openRepeatingDelimiter, final String closeRepeatingDelimiter,
				final String openRepeatingExcludedDelimiter, final String closeRepeatingExcludedDelimiter) {

			this.openRepeatingDelimiter = openRepeatingDelimiter ;
			this.closeRepeatingDelimiter = closeRepeatingDelimiter ;
			this.openRepeatingExcludedDelimiter = openRepeatingExcludedDelimiter ;
			this.closeRepeatingExcludedDelimiter = closeRepeatingExcludedDelimiter ;
			return this ;
		}


		/**
		 *
		 */
		public Templator$Delimited() {}


		/**
		 * @param template
		 * @return
		 */
		@Log
		public Templator$TemplatedAsStringBuilder template(final StringBuilder template) {
			return new Templator() {}.new Templator$TemplatedAsStringBuilder(this, template) ;
		}

		@Log
		public Templator$TemplatedAsString template(final String template) {
			return new Templator() {}.new Templator$TemplatedAsString(this, template) ;
		}
	}


//	@Log
//	private Templator() {
//		throw new UnsupportedOperationException() ;
//	}


	@Log
	public static Templator$TemplatedAsStringBuilder template(final StringBuilder template) {
		return new Templator() {}.new Templator$Delimited().template(template) ;
	}

	@Log
	public static Templator$TemplatedAsString template(final String template) {
		return new Templator() {}.new Templator$Delimited().template(template) ;
	}


	//
	// Delimiters ...
	//

	/**
	 * @return
	 */
	@Log
	public static String openTagDelimiter() {
		return new Templator() {}.new Templator$Delimited().openTagDelimiter() ;
	}

	@Log
	public static Templator$Delimited openTagDelimiter(final String openTagDelimiter) {
		return new Templator() {}.new Templator$Delimited().openTagDelimiter(openTagDelimiter) ;
	}


	/**
	 * @return
	 */
	@Log
	public static String closeTagDelimiter() {
		return new Templator() {}.new Templator$Delimited().closeTagDelimiter() ;
	}

	@Log
	public static Templator$Delimited closeTagDelimiter(final String closeTagDelimiter) {
		return new Templator() {}.new Templator$Delimited().closeTagDelimiter(closeTagDelimiter) ;
	}


	/**
	 * @return
	 */
	@Log
	public static String openRepeatingDelimiter() {
		return new Templator() {}.new Templator$Delimited().openRepeatingDelimiter() ;
	}

	@Log
	public static Templator$Delimited openRepeatingDelimiter(final String openRepeatingDelimiter) {
		return new Templator() {}.new Templator$Delimited().openRepeatingDelimiter(openRepeatingDelimiter) ;
	}


	/**
	 * @return
	 */
	@Log
	public static String closeRepeatingDelimiter() {
		return new Templator() {}.new Templator$Delimited().closeRepeatingDelimiter() ;
	}

	@Log
	public static Templator$Delimited closeRepeatingDelimiter(final String closeRepeatingDelimiter) {
		return new Templator() {}.new Templator$Delimited().closeRepeatingDelimiter(closeRepeatingDelimiter) ;
	}


	/**
	 * @return
	 */
	@Log
	public static String openRepeatingExcludedDelimiter() {
		return new Templator() {}.new Templator$Delimited().openRepeatingExcludedDelimiter() ;
	}

	@Log
	public static Templator$Delimited openRepeatingExcludedDelimiter(final String openRepeatingExcludedDelimiter) {
		return new Templator() {}.new Templator$Delimited().openRepeatingExcludedDelimiter(openRepeatingExcludedDelimiter) ;
	}


	/**
	 * @return
	 */
	@Log
	public static String closeRepeatingExcludedDelimiter() {
		return new Templator() {}.new Templator$Delimited().closeRepeatingExcludedDelimiter() ;
	}

	@Log
	public static Templator$Delimited closeRepeatingExcludedDelimiter(final String closeRepeatingExcludedDelimiter) {
		return new Templator() {}.new Templator$Delimited().closeRepeatingExcludedDelimiter(closeRepeatingExcludedDelimiter) ;
	}


	/**
	 * @return
	 */
	@Log
	public static String[] delimiters() {
		return new Templator() {}.new Templator$Delimited().delimiters() ;
	}

	@Log
	public static Templator$Delimited delimiters(final String... delimiters) {

		return new Templator() {}.new Templator$Delimited() //
				.openTagDelimiter(delimiters[0]) //
				.closeTagDelimiter(delimiters[1]) //
				.openRepeatingDelimiter(delimiters[2]) //
				.closeRepeatingDelimiter(delimiters[3]) //
				.openRepeatingExcludedDelimiter(delimiters[4]) //
				.closeRepeatingExcludedDelimiter(delimiters[5]) ;
	}


	/**
	 * @return
	 */
	@Log
	public static String[] tagDelimiters() {
		return new Templator() {}.new Templator$Delimited().tagDelimiters() ;
	}

	@Log
	public static Templator$Delimited tagDelimiters(final String openTagDelimiter, final String closeTagDelimiter) {
		return new Templator() {}.new Templator$Delimited() //
				.openTagDelimiter(openTagDelimiter) //
				.closeTagDelimiter(closeTagDelimiter) ;
	}


	/**
	 * @return
	 */
	@Log
	public static String[] repeatingDelimiters() {
		return new Templator() {}.new Templator$Delimited().repeatingDelimiters() ;
	}

	@Log
	public static Templator$Delimited repeatingDelimiters(final String openRepeatingDelimiter, final String closeRepeatingDelimiter,
			final String openRepeatingExcludedDelimiter, final String closeRepeatingExcludedDelimiter) {

		return new Templator() {}.new Templator$Delimited() //
				.openRepeatingDelimiter(openRepeatingDelimiter) //
				.closeRepeatingDelimiter(closeRepeatingDelimiter) //
				.openRepeatingExcludedDelimiter(openRepeatingExcludedDelimiter) //
				.closeRepeatingExcludedDelimiter(closeRepeatingExcludedDelimiter) ;
	}
}
