

package xcom.utils4j.resources ;


import java.io.ByteArrayOutputStream ;
import java.io.File ;
import java.io.FileInputStream ;
import java.io.IOException ;
import java.io.InputStream ;
import java.text.ParseException ;

import org.apache.commons.io.FilenameUtils ;
import org.apache.commons.lang3.StringUtils ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


public class Files {

	static final int ShortcutLengthMin = 0x64 ;
	static final int MagicAtrbLength = 0x20 ;
	static final int MagicFlagsOffset = 0x14 ;


	@Log
	private Files() {
		throw new UnsupportedOperationException() ;
	}


	@Log /* (value = Level.INFO) */
	public static String coerceFullPathSpec(final String... specs) {
//		System.out.println(Arrays.asList(specs)) ;

		int i = 0 ;
		String spec = "" ;

		while ( i < specs.length ) {

			if ( StringUtils.trimToEmpty(FilenameUtils.getExtension(spec)).isEmpty()
					&& !StringUtils.trimToEmpty(FilenameUtils.getExtension(specs[i])).isEmpty() )
				spec = spec + '.' + FilenameUtils.getExtension(specs[i]) ;
//			System.out.println("1>" + spec) ;

			if ( StringUtils.trimToEmpty(FilenameUtils.getBaseName(spec)).isEmpty() && !StringUtils.trimToEmpty(FilenameUtils.getBaseName(specs[i])).isEmpty() )
				spec = FilenameUtils.getBaseName(specs[i]) + spec ;
//			System.out.println("2>" + spec) ;

			if ( StringUtils.trimToEmpty(FilenameUtils.getPrefix(spec)).isEmpty() && !StringUtils.trimToEmpty(FilenameUtils.getPath(specs[i])).isEmpty()
					&& StringUtils.trimToEmpty(FilenameUtils.getPrefix(specs[i])).isEmpty() )
				spec = FilenameUtils.getPathNoEndSeparator(specs[i]) + "\\" + spec ;
//			System.out.println("3>" + spec) ;

			if ( StringUtils.trimToEmpty(FilenameUtils.getPrefix(spec)).isEmpty() && !StringUtils.trimToEmpty(FilenameUtils.getPrefix(specs[i])).isEmpty() )
				spec = FilenameUtils.getFullPathNoEndSeparator(specs[i]) + "\\" + spec ;
//			System.out.println("4>" + spec) ;

			i++ ;
		}

		return FilenameUtils.normalize(spec) ;
	}


	@Log
	public static String getAbsoluteBaseName(final String fnSpec) {

		String baseName = FilenameUtils.getBaseName(fnSpec) ;
		while ( !baseName.equals((baseName = FilenameUtils.getBaseName(baseName))) ) {}

		return baseName ;
	}


	@Log
	public static File coerceFileFromLink(final File file) {

		if ( isPotentialValidLink(file) )
			try ( InputStream in = new FileInputStream(file.getCanonicalPath() + ".lnk"); ) {
				return new File(parseWindowsShortcut(parseWindowsShortcut_getBytes(in))) ;
			}
			catch ( ParseException | IOException e ) {}


		return file ;
	}


	@Log
	public static File parentedFile(final String fileSpec) {

		File file ;
		File parent ;

		if ( ((parent = (file = new File(fileSpec)).getParentFile()) != null) && !parent.exists() )
			parent.mkdirs() ;

		return file ;
	}


	@Log
	public static boolean isPotentialValidLink(final File file) {

		try {
			final File _file = new File(file.getCanonicalPath() + ".lnk") ;
			try ( InputStream fis = new FileInputStream(_file); ) {
				return _file.isFile() && _file.getName().toLowerCase().endsWith(".lnk") && (fis.available() >= ShortcutLengthMin)
						&& parseWindowsShortcut_isMagicPresent(parseWindowsShortcut_getBytes(fis, MagicAtrbLength)) ;
			}
		}
		catch ( final IOException e ) {}

		return false ;
	}


	static String parseWindowsShortcut(final byte[] link) throws ParseException {

		try {
			if ( !parseWindowsShortcut_isMagicPresent(link) )
				throw new ParseException("Invalid shortcut; magic is missing", 0) ;

			// get the flags byte
			final byte flags = link[MagicFlagsOffset] ;

			// get the file attributes byte
			final int file_atts_offset = 0x18 ;
			final byte file_atts = link[file_atts_offset] ;
			final byte is_dir_mask = (byte) 0x10 ;

			final boolean directory = (file_atts & is_dir_mask) > 0 ;

			// if the shell settings are present, skip them
			final int shell_offset = 0x4c ;
			final byte has_shell_mask = (byte) 0x01 ;

			final int shell_len = ((flags & has_shell_mask) > 0 ? parseWindowsShortcut_bytesToWord(link, shell_offset) + 2 : 0) ;


			// get to the file settings
			final int file_start = 0x4c + shell_len ;

			final int file_location_info_flag_offset_offset = 0x08 ;
			final int file_location_info_flag = link[file_start + file_location_info_flag_offset_offset] ;
			final boolean local = (file_location_info_flag & 2) == 0 ;
			// get the local volume and local system values
			// final int localVolumeTable_offset_offset = 0x0C;
			final int basename_offset_offset = 0x10 ;
			final int networkVolumeTable_offset_offset = 0x14 ;
			final int finalname_offset_offset = 0x18 ;
			final int finalname_offset = link[file_start + finalname_offset_offset] + file_start ;
			final String finalname = parseWindowsShortcut_getNullDelimitedString(link, finalname_offset) ;

//			if ( local ) {
			final int basename_offset = link[file_start + basename_offset_offset] + file_start ;
			final String basename = parseWindowsShortcut_getNullDelimitedString(link, basename_offset) ;
			return basename + finalname ;
//			}
//			else {
//				int networkVolumeTable_offset = link[file_start + networkVolumeTable_offset_offset] + file_start ;
//				int shareName_offset_offset = 0x08 ;
//				int shareName_offset = link[networkVolumeTable_offset + shareName_offset_offset] + networkVolumeTable_offset ;
//				String shareName = getNullDelimitedString(link, shareName_offset) ;
//				return shareName + "\\" + finalname ;
//			}
		}
		catch ( final ArrayIndexOutOfBoundsException e ) {
			throw new ParseException("Could not be parsed, probably not a valid WindowsShortcut", 0) ;
		}
	}


	static byte[] parseWindowsShortcut_getBytes(final InputStream in) throws IOException {
		return parseWindowsShortcut_getBytes(in, null) ;
	}


	static byte[] parseWindowsShortcut_getBytes(final InputStream in, Integer max) throws IOException {

		// read the entire file into a byte buffer
		final ByteArrayOutputStream bout = new ByteArrayOutputStream() ;
		final byte[] buff = new byte[256] ;
		while ( (max == null) || (max > 0) ) {
			final int n = in.read(buff) ;
			if ( n == -1 )
				break ;
			bout.write(buff, 0, n) ;
			if ( max != null )
				max -= n ;
		}

		return bout.toByteArray() ;
	}


	static boolean parseWindowsShortcut_isMagicPresent(final byte[] link) {

		final int magic = 0x0000004C ;
		final int magic_offset = 0x00 ;
		return (link.length >= 32) && (parseWindowsShortcut_bytesToDword(link, magic_offset) == magic) ;
	}


	static String parseWindowsShortcut_getNullDelimitedString(final byte[] bytes, final int off) {

		int len = 0 ;
		// count bytes until the null character (0)
		while ( true ) {
			if ( bytes[off + len] == 0 )
				break ;
			len++ ;
		}

		return new String(bytes, off, len) ;
	}


	/*
	 * Convert two bytes into a short: note, this is little endian because it's for an Intel only OS.
	 */
	static int parseWindowsShortcut_bytesToDword(final byte[] bytes, final int off) {
		return (parseWindowsShortcut_bytesToWord(bytes, off + 2) << 16) | parseWindowsShortcut_bytesToWord(bytes, off) ;
	}


	static int parseWindowsShortcut_bytesToWord(final byte[] bytes, final int off) {
		return ((bytes[off + 1] & 0xff) << 8) | (bytes[off] & 0xff) ;
	}
}
