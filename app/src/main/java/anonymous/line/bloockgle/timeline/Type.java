package anonymous.line.bloockgle.timeline;

import java.util.List;
import java.util.ArrayList;

public enum Type {
	WEB, TORRENT, PRODUCT, REWARD, CROWDFUNDING, INVEST, CURRICULUM, JOBS, LEAKS;

	@Override
	public String toString() {
		return toString(this);
	}

	public List<String> getKeyword() {
		return getKeyword(this);
	}

	public String toUpperCase(){
		String name = toString();
		name = String.valueOf(name.charAt(0)).toUpperCase() + name.subSequence(1, name.length());
		return name;
	}

	public static Type getType(String string){
		switch (string){
			case "web":
				return WEB;
			case "torrent":
				return TORRENT;
			case "product":
				return PRODUCT;
			case "reward":
				return REWARD;
			case "crowdfunding":
				return CROWDFUNDING;
			case "invest":
				return INVEST;
			case "curriculum":
				return CURRICULUM;
			case "jobs":
				return JOBS;
			default:
				return LEAKS;
		}
	}

	public static List<String> getKeyword(Type type) {
		List<String> keys = new ArrayList<String>();
		switch(type) {
			case WEB:
				keys.add("link");
				break;
			case TORRENT:
				keys.add("torrent_link");
				break;
			case PRODUCT:
				keys.add("price");
				keys.add("product_link");
				keys.add("link");
				break;
			case REWARD:
				keys.add("link");
				break;
			case CROWDFUNDING:
				keys.add("money_need");
				keys.add("web_page");
				break;
			case INVEST:
				keys.add("promise");
				keys.add("references");
				keys.add("contact");
				keys.add("project_link");
				break;
			case CURRICULUM:
				keys.add("profession");
				keys.add("city");
				keys.add("country");
				keys.add("salary");
				keys.add("date_to_start");
				keys.add("contact");
				break;
			case JOBS:
				keys.add("link");
				break;
			default:
				keys.add("link");
				break;
		}
		return keys;
	}

	public static String toString(Type type) {
		switch(type) {
			case WEB:
				return "web";
			case TORRENT:
				return "torrent";
			case PRODUCT:
				return "product";
			case REWARD:
				return "reward";
			case CROWDFUNDING:
				return "crowdfunding";
			case INVEST:
				return "invest";
			case CURRICULUM:
				return "curriculum";
			case JOBS:
				return "jobs";
			default:
				return "leaks";
		}
	}
}
