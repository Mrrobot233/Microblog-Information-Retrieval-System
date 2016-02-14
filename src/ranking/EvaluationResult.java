package ranking;

public class EvaluationResult implements Comparable<EvaluationResult>{
	
	private String topicId;
	private String q0 = "Q0";
	private String docNum = "";
	private double cosineSimilarityValue = 0.000;
	private String tag;
	
	public EvaluationResult() {
		
	}

	public String getTopicId() {
		return topicId;
	}

	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}

	public String getQ0() {
		return q0;
	}

	public void setQ0(String q0) {
		this.q0 = q0;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public double getCosineSimilarityValue() {
		return cosineSimilarityValue;
	}

	public void setCosineSimilarityValue(double cosineSimilarityValue) {
		this.cosineSimilarityValue = cosineSimilarityValue;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public int compareTo(EvaluationResult evaluationResult) {
		return Double.compare(this.getCosineSimilarityValue(), evaluationResult.getCosineSimilarityValue());
	}
	
	
}
