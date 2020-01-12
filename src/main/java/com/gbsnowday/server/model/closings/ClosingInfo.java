
package com.gbsnowday.server.model.closings;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClosingInfo implements Serializable
{

    @SerializedName("record")
    @Expose
    private List<ClosingRecord> closingRecord = null;
    @SerializedName("locations")
    @Expose
    private List<String> locations = null;
    @SerializedName("num_closings")
    @Expose
    private Integer numClosings;
    @SerializedName("export_type")
    @Expose
    private String exportType;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("run_date")
    @Expose
    private String runDate;
    private final static long serialVersionUID = 6609437220116406990L;

    public List<ClosingRecord> getClosingRecord() {
        return closingRecord;
    }

    public void setClosingRecord(List<ClosingRecord> closingRecord) {
        this.closingRecord = closingRecord;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public Integer getNumClosings() {
        return numClosings;
    }

    public void setNumClosings(Integer numClosings) {
        this.numClosings = numClosings;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

}
