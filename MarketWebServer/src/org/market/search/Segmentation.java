package org.market.search;

import java.io.IOException;
import java.io.StringReader;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class Segmentation{
	
	public static String IKAnalyze(String key)throws IOException{
		String keyAfterIKAalyze = "";
		StringReader reader = new StringReader(key);
		IKSegmenter ik = new IKSegmenter(reader, false);
		Lexeme lexeme = null;
		while((lexeme = ik.next())!= null)
			keyAfterIKAalyze += lexeme.getLexemeText() + '|';
		return keyAfterIKAalyze;
	}
}
