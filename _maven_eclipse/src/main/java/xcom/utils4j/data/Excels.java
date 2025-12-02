

package xcom.utils4j.data ;


import java.time.format.DateTimeFormatter ;
import java.util.ArrayList ;
import java.util.Collection ;
import java.util.HashMap ;
import java.util.LinkedHashMap ;
import java.util.List ;
import java.util.Map ;
import java.util.Map.Entry ;

import org.apache.commons.lang3.StringUtils ;
import org.apache.poi.ss.usermodel.Cell ;
import org.apache.poi.ss.usermodel.CellValue ;
import org.apache.poi.ss.usermodel.DateUtil ;
import org.apache.poi.ss.usermodel.FormulaEvaluator ;
import org.apache.poi.ss.usermodel.Row ;
import org.apache.poi.ss.usermodel.Sheet ;
import org.apache.poi.ss.usermodel.Workbook ;

import xcom.utils4j.logging.aspects.api.annotations.Log ;


/**
 * Simple wrappers around Apache POI methods.
 */
@Log
public abstract class Excels {

//	static final String COLUMN_NAMES = "  A  B  C  D  E  F  G  H  I  J  K  L  M  N  O  P  Q  R  S  T  U  V  W  X  Y  Z"
//			+ " AA AB AC AD AE AF AG AH AI AJ AK AL AM AN AO AP AQ AR AS AT AU AV AW AX AY AZ"
//			+ " BA BB BC BD BE BF BG BH BI BJ BK BL BM BN BO BP BQ BR BS BT BU BV BW BX BY BZ"
//			+ " CA CB CC CD CE CF CG CH CI CJ CK CL CM CN CO CP CQ CR CS CT CU CV CW CX CY CZ"
//			+ " DA DB DC DD DE DF DG DH DI DJ DK DL DM DN DO DP DQ DR DS DT DU DV DW DX DY DZ"
//			+ " EA EB EC ED EE EF EG EH EI EJ EK EL EM EN EO EP EQ ER ES ET EU EV EW EX EY EZ"
//			+ " FA FB FC FD FE FF FG FH FI FJ FK FL FM FN FO FP FQ FR FS FT FU FV FW FX FY FZ"
//			+ " GA GB GC GD GE GF GG GH GI GJ GK GL GM GN GO GP GQ GR GS GT GU GV GW GX GY GZ"
//			+ " HA HB HC HD HE HF HG HH HI HJ HK HL HM HN HO HP HQ HR HS HT HU HV HW HX HY HZ"
//			+ " IA IB IC ID IE IF IG IH II IJ IK IL IM IN IO IP IQ IR IS IT IU IV IW IX IY IZ"
//			+ " JA JB JC JD JE JF JG JH JI JJ JK JL JM JN JO JP JQ JR JS JT JU JV JW JX JY JZ"
//			+ " KA KB KC KD KE KF KG KH KI KJ KK KL KM KN KO KP KQ KR KS KT KU KV KW KX KY KZ"
//			+ " LA LB LC LD LE LF LG LH LI LJ LK LL LM LN LO LP LQ LR LS LT LU LV LW LX LY LZ"
//			+ " MA MB MC MD ME MF MG MH MI MJ MK ML MM MN MO MP MQ MR MS MT MU MV MW MX MY MZ"
//			+ " NA NB NC ND NE NF NG NH NI NJ NK NL NM NN NO NP NQ NR NS NT NU NV NW NX NY NZ"
//			+ " OA OB OC OD OE OF OG OH OI OJ OK OL OM ON OO OP OQ OR OS OT OU OV OW OX OY OZ"
//			+ " PA PB PC PD PE PF PG PH PI PJ PK PL PM PN PO PP PQ PR PS PT PU PV PW PX PY PZ" ;
	static String COLUMN_NAMES = "" ;
	static {    // '  A' thru 'AZZ'
		" A".chars().forEach(c0 -> //
		" ABCDEFGHIJKLMNOPQRSTUVWXYZ".chars().forEach(c1 -> //
		"ABCDEFGHIJKLMNOPQRSTUVWXYZ".chars().forEach(c2 -> //
		COLUMN_NAMES += String.valueOf((char) c0) + String.valueOf((char) c1) + String.valueOf((char) c2)))) ;
	}

	static final int MaxSupportedColumns = (short) (Excels.COLUMN_NAMES.length() / 3) - 1 ;

	static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyyy-MM-dd") ;

	public static final boolean RELATIVE_REFERENCE = false ;

	public static final boolean ABSOLUTE_REFERENCE = !RELATIVE_REFERENCE ;

	public static final boolean RELATIVE_COLUMN_REFERENCE = false ;

	public static final boolean ABSOLUTE_COLUMN_REFERENCE = !RELATIVE_COLUMN_REFERENCE ;

	public static final boolean RELATIVE_ROW_REFERENCE = false ;

	public static final boolean ABSOLUTE_ROW_REFERENCE = !RELATIVE_ROW_REFERENCE ;

	public static final boolean DO_NOT_TRIM_NON_PRINTABLE_CHARACTERS = false ;

	public static final boolean TRIM_NON_PRINTABLE_CHARACTERS = !DO_NOT_TRIM_NON_PRINTABLE_CHARACTERS ;


	private Excels() {
		throw new UnsupportedOperationException() ;
	}


	/**
	 * Return an Excel style cell reference. (ie. <code>A1</code>, <code>P12</code>, etc.)
	 *
	 * @param r
	 *            - The referenced cell row
	 * @param c
	 *            - The referenced cell column
	 * @return The generated Excel reference
	 */
	public static String cellRef(final int r, final int c) {
		return cellRef(r, c, RELATIVE_REFERENCE, RELATIVE_REFERENCE) ;
	}


	/**
	 * Return a relative or absolute Excel style cell reference. (ie. <code>$A$1</code>, <code>P12</code>, etc.)
	 *
	 * @param r
	 *            - The referenced cell row
	 * @param c
	 *            - The referenced cell column
	 * @param abs
	 *            - <code>RELATIVE_REFERENCE</code> or <code>ABSOLUTE_REFERENCE</code>
	 * @return The generated Excel reference
	 */
	public static String cellRef(final int r, final int c, final boolean abs) {
		return cellRef(r, c, abs, abs) ;
	}


	/**
	 * Return a relative or absolute, row and/or column Excel style cell reference. (ie. <code>$A1</code>, <code>$P$12</code>, etc.)
	 *
	 * @param r
	 *            - The referenced cell's row
	 * @param c
	 *            - The referenced cell's column
	 * @param absRow
	 *            - <code>RELATIVE_ROW_REFERENCE</code> or <code>ABSOLUTE_ROW_REFERENCE</code>
	 * @param absColumn
	 *            - <code>RELATIVE_COLUMN_REFERENCE</code> or <code>ABSOLUTE_COLUMN_REFERENCE</code>
	 * @return The generated Excel reference
	 */
	public static String cellRef(final int r, final int c, final boolean absRow, final boolean absColumn) {

		if ( (r < 0) || (c < 0) || (c > MaxSupportedColumns) )
			throw new IllegalArgumentException() ;

		return (absColumn ? "$" : "") + COLUMN_NAMES.substring(c * 3, (c + 1) * 3).trim() + (absRow ? "$" : "") + (r + 1) ;
	}


	public static String getCellValueAsString(final Cell cell) {
		return getCellValueAsString(cell, false) ;
	}


	/**
	 * Return a <code>cell</code>'s value as a string.
	 *
	 * @param cell
	 *            - The <code>cell</code>
	 * @return The <code>cell</code> value as a string. <br />
	 *         Returns an empty string if <code>cell</code> is <code>null</code> .
	 */
	public static String getCellValueAsString(final Cell cell, final boolean stripNonPrintiable) {

		String results = "" ;

		if ( cell != null )
			switch ( cell.getCellType() ) {
				case BOOLEAN:
					results = Boolean.toString(cell.getBooleanCellValue()).trim() ;
					break ;

				case FORMULA:
					final FormulaEvaluator eval = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator() ;
					final CellValue cellValue = eval.evaluate(cell) ;

					switch ( cellValue.getCellType() ) {
						case BOOLEAN:
							results = Boolean.toString(cellValue.getBooleanValue()).trim() ;
							break ;

						case ERROR:
							break ;

						case NUMERIC:
							if ( DateUtil.isCellDateFormatted(cell) )
								results = DTF.format(cell.getLocalDateTimeCellValue()) ;

							else {
								results = Double.toString(cell.getNumericCellValue()).trim() ;

								if ( results.endsWith(".0") )
									results = results.substring(0, results.length() - 2) ;
							}
							break ;

						case STRING:
							results = cellValue.getStringValue() ;
							break ;

						case BLANK:
						case FORMULA:
						case _NONE:
						default:
							break ;
					}
					break ;

				case NUMERIC:
					if ( DateUtil.isCellDateFormatted(cell) )
						results = DTF.format(cell.getLocalDateTimeCellValue()) ;

					else {
						results = Double.toString(cell.getNumericCellValue()).trim() ;

						if ( results.endsWith(".0") )
							results = results.substring(0, results.length() - 2) ;
					}
					break ;

				case STRING:
					results = cell.getRichStringCellValue().getString() ;
					break ;

				case BLANK:
				case ERROR:
				case _NONE:
				default:
					break ;
			}

		results = StringUtils.trimToEmpty(results) ;

		if ( stripNonPrintiable )
			results = results.replaceAll("[^\\x00-\\x7F]", "") ;

		return results ;
	}


	/**
	 * @param row
	 * @param cIdx
	 * @param value
	 * @return
	 */
	public static Cell setCellValue(final Row row, final Short cIdx, final String value) {

		Cell cell = row.getCell(cIdx) ;

		if ( cell == null )
			cell = row.createCell(cIdx) ;

		cell.setCellValue(value) ;

		return cell ;
	}


	/**
	 * Returns a <code>cell</code> from a <code>row</code>.
	 *
	 * @param row
	 *            An Excel <code>row</code>.
	 * @param c
	 *            The indexed column to return. (Zero based.)
	 * @return The indexed <code>cell<code>.
	 * <br />
	 * If the referenced <code>cell</code> is not defined in the <code>row</code>, a new <code>cell</code> is created in the <code>row</code> and returned.
	 */
	public static Cell getCell(final Row row, final int c) {

		if ( c < 0 )
			throw new IllegalArgumentException() ;

		Cell results = row.getCell(c) ;
		if ( results == null )
			results = row.createCell(c) ;

		return results ;
	}


	/**
	 * @param sheet
	 *            An Excel <code>sheet</code>.
	 * @param r
	 *            The indexed row to return. (Zero based.)
	 * @return The indexed <code>row<code>.
	 * <br />
	 * If the referenced <code>row</code> is not defined in the <code>sheet</code>, a new <code>row</code> is created in the <code>sheet</code> and returned.
	 */
	public static Row getRow(final Sheet sheet, final int r) {

		if ( r < 0 )
			throw new IllegalArgumentException() ;

		Row results = sheet.getRow(r) ;
		if ( results == null )
			results = sheet.createRow(r) ;

		return results ;
	}


	public static Map<String, Short> readColumnHeadersAndIndex(final Sheet sheet, final boolean isExcludeEmptyHeaders) {
		return indexColumnHeaders(sheet, sheet.getRow(0), isExcludeEmptyHeaders) ;
	}


	public static Map<String, Short> indexColumnHeaders(final Sheet sheet, final Row row, final boolean isExcludeEmptyHeaders) {

		final HashMap<String, Short> results = new LinkedHashMap<>() ;

		for ( short c = 0; (c < row.getLastCellNum()); c++ )
			if ( !sheet.isColumnHidden(c) ) {
				final String header = getCellValueAsString(row.getCell(c), TRIM_NON_PRINTABLE_CHARACTERS) ;
				if ( !isExcludeEmptyHeaders || (isExcludeEmptyHeaders && !header.isEmpty()) )
					results.put(header, c) ;
			}

		return results ;
	}


	public static Collection<Map<String, List<Entry<String, String>>>> readWorkbookAsCollection(final Workbook workbook) {

		final Collection<Map<String, List<Entry<String, String>>>> data = new ArrayList<>() ;
		final Map<String, List<List<Entry<String, String>>>> sheets = new LinkedHashMap<>() ;
		final List<Integer> rowsToSkip = new ArrayList<>() ;

		for ( int sIdx = 0; sIdx < workbook.getNumberOfSheets(); sIdx++ )
			if ( !workbook.isSheetHidden(sIdx) && !workbook.getSheetAt(sIdx).getSheetName().startsWith("!") ) {
				final Sheet sheet = workbook.getSheetAt(sIdx) ;
				final List<List<Entry<String, String>>> rows = new ArrayList<>() ;
				final Map<String, Short> headers = Excels.readColumnHeadersAndIndex(sheet, true) ;

				for ( int rIdx = 1; (rIdx <= sheet.getLastRowNum()); rIdx++ ) {

					if ( (sheet.getRow(rIdx) != null) && (headers.get("!status") != null) && (sheet.getRow(rIdx).getCell(headers.get("!status")) != null)
							&& StringUtils.trimToEmpty(Excels.getCellValueAsString(sheet.getRow(rIdx).getCell(headers.get("!status")))).equals("!") )

						rowsToSkip.add(rIdx - 1) ;

					rows.add(readRowAsListOfMapEntry(sheet.getRow(rIdx), headers, "!")) ;
				}

				sheets.put(sheet.getSheetName(), rows) ;
			}

		int rowsMax = 0 ;
		for ( final Entry<String, List<List<Entry<String, String>>>> rows : sheets.entrySet() )
			if ( rows.getValue().size() > rowsMax )
				rowsMax = rows.getValue().size() ;

		for ( int rIdx = 0; rIdx < rowsMax; rIdx++ ) {

			final Map<String, List<Entry<String, String>>> row = new LinkedHashMap<>() ;

			if ( !rowsToSkip.contains(rIdx) ) {
				for ( final Entry<String, List<List<Entry<String, String>>>> sheet : sheets.entrySet() )
					if ( sheet.getValue().get(rIdx).size() > 0 )
						row.put(sheet.getKey(), sheet.getValue().get(rIdx)) ;

				data.add(row) ;
			}
		}

		return data ;
	}


	public static Collection<List<Entry<String, String>>> readSheetAsCollection(final Sheet sheet) {
		return Excels.readSheetAsCollection(sheet, "!status", "!", "!") ;
	}


	static Collection<List<Entry<String, String>>> readSheetAsCollection(final Sheet sheet, final String skipColName, final String skipRowValue,
			final String skipColPrefix) {

		final Collection<List<Entry<String, String>>> data = new ArrayList<>() ;
		final Map<String, Short> headers = Excels.readColumnHeadersAndIndex(sheet, true) ;

		for ( int rIdx = 1; (rIdx <= sheet.getLastRowNum()); rIdx++ ) // Skip row 0 (column headers).

			if ( (skipColName == null) || (headers.get(skipColName) == null)
					|| !StringUtils.trimToEmpty(Excels.getCellValueAsString(sheet.getRow(rIdx).getCell(headers.get(skipColName)))).equals(skipRowValue) )

				if ( sheet.getRow(rIdx) != null )
					data.add(readRowAsListOfMapEntry(sheet.getRow(rIdx), headers, skipColPrefix)) ;

		return data ;
	}


	static List<Entry<String, String>> readRowAsListOfMapEntry(final Row row, final Map<String, Short> headers, final String skipColPrefix) {

		final List<Entry<String, String>> cols = new ArrayList<>() ;

		if ( row != null )

			for ( final Entry<String, Short> header : headers.entrySet() )

				if ( ((skipColPrefix == null) || !header.getKey().startsWith(skipColPrefix))
						&& !StringUtils.isBlank(Excels.getCellValueAsString(row.getCell(header.getValue()))) )

					cols.add(Map.entry(header.getKey(), StringUtils.trimToEmpty(Excels.getCellValueAsString(row.getCell(header.getValue()))))) ;

		return cols ;
	}
}
