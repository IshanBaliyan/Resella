package resella;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class RecentSearchesFileManager {
	private String filePath;
	private RecentSearches recentSearches;

	public RecentSearchesFileManager() {
		this("recentSearches.yaml");
	}

	public RecentSearchesFileManager(String filePath) {
		this.filePath = filePath;
		Yaml yaml = new Yaml();
		try {
			InputStream input = new FileInputStream(new File(filePath));
			Map<String, Object> map = yaml.load(input);
			System.out.println(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
