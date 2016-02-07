package ranking;

public class Query {

	private String queryId = "";
	private String title = "";
	
	public Query () {
		
	}
	
	public Query(String queryId, String title) {
		this.queryId = queryId;
		this.title = title;
	}
	
	public String getId() {
		return queryId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setId(String queryId) {
		this.queryId = queryId;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
}
