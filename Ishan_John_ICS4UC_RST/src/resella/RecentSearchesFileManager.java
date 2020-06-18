package resella;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;


//Class not implemented in program yet
public class RecentSearchesFileManager {
	private String filePath;
	private RecentSearches recentSearches;

	public RecentSearchesFileManager() {
		this("data//recentSearches.yaml");
	}

	public RecentSearchesFileManager(String filePath) {
		this.filePath = filePath;
		Yaml yaml = new Yaml(new Constructor(RecentSearches.class));
		try {
			InputStream input = new FileInputStream(new File(filePath));
			RecentSearches recentSearches = yaml.load(input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
