

package xcom.utils4j.resources ;


import static org.junit.Assert.assertEquals ;

import java.util.Arrays ;
import java.util.Collection ;

import org.junit.Test ;
import org.junit.runner.RunWith ;
import org.junit.runners.Parameterized ;


@RunWith(Parameterized.class)
public class _Test_Files_ParseFileSpec {

	@Parameterized.Parameters
	public static Collection<Object[]> data() {

		//@formatter:off
		
        final Object[][] results = {
        		
        	// fileSpec                           device  path            fnSpec             bnSpec         filename  fnExtSpec  extSpec        extension
        		
        	{ "C:\\dir1\\dir2\\abc.def.xyz.txt", "C:",   "\\dir1\\dir2", "abc.def.xyz.txt", "abc.def.xyz", "abc",    "def.xyz", "def.xyz.txt", "txt", },
        		
      		{ "abc.txt",                         "",     "\\",           "abc.txt",         "abc",         "abc",    "",        "txt",         "txt", },
      		{ "abc",                             "",     "\\",           "abc",             "abc",         "abc",    "",        "",            "",    },
        	{ ".txt",                            "",     "\\",           ".txt",            "",             "",      "",        "txt",         "txt", },
    		
        	{ "\\\\server\\abc.txt",             "\\\\server", "\\",     "abc.txt",         "abc",         "abc",    "",        "txt",         "txt", },
        } ;
        
        //@formatter:on

		return (Arrays.asList(results)) ;
	}


	//
	//
	//

	String fileSpec ;
	String device ;
	String pathSpec ;
	String fnSpec ;
	String bnSpec ;
	String filename ;
	String fnExtSpec ;
	String extSpec ;
	String extension ;


	//
	//
	//

	public _Test_Files_ParseFileSpec(final String fileSpec, final String device, final String pathSpec, final String fnSpec, final String bnSpec,
			final String filename, final String fnExtSpec, final String extSpec, final String extension) {

		this.fileSpec = fileSpec ;
		this.device = device ;
		this.pathSpec = pathSpec ;
		this.fnSpec = fnSpec ;
		this.bnSpec = bnSpec ;
		this.filename = filename ;
		this.fnExtSpec = fnExtSpec ;
		this.extSpec = extSpec ;
		this.extension = extension ;
	}


	//
	//
	//

	@Test
	public void test_device() {
		assertEquals("device not parsed correctly: ", device, Files.device(fileSpec)) ;
	}

	@Test
	public void test_pathSpec() {
		assertEquals("pathSpec not parsed correctly: ", pathSpec, Files.pathSpec(fileSpec)) ;
	}

	@Test
	public void test_fnSpec() {
		assertEquals("fnSpec not parsed correctly: ", fnSpec, Files.fnSpec(fileSpec)) ;
	}

	@Test
	public void test_bnSpec() {
		assertEquals("bnSpec not parsed correctly: ", bnSpec, Files.bnSpec(fileSpec)) ;
	}

	@Test
	public void test_filename() {
		assertEquals("filename not parsed correctly: ", filename, Files.filename(fileSpec)) ;
	}

	@Test
	public void test_fnExtSpec() {
		assertEquals("fnExtSpec not parsed correctly: ", fnExtSpec, Files.fnExtSpec(fileSpec)) ;
	}

	@Test
	public void test_extSpec() {
		assertEquals("extSpec not parsed correctly: ", extSpec, Files.extSpec(fileSpec)) ;
	}

	@Test
	public void test_extension() {
		assertEquals("extension not parsed correctly: ", extension, Files.extension(fileSpec)) ;
	}
}
