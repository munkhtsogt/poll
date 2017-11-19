package models;

final public class ReportFactory {
	private ReportFactory(){};
	public static Report createReport(String type){
		return new ReportImpl(type);
	}
}
