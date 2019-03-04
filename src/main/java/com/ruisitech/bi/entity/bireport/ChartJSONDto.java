//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.entity.bireport;

import com.ruisitech.bi.entity.portal.LinkAcceptDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartJSONDto {
    private String type;
    private Integer typeIndex;
    private List<DimDto> params;
    private String label;
    private DimDto xcol;
    private DimDto scol;
    private DimDto ycol;
    private Map<String, Object> link;
    private LinkAcceptDto linkAccept;
    private String maparea;
    private String mapAreaName;
    private Integer height;
    private String width;
    private String showLegend;
    private String legendLayout;
    private String legendpos;
    private String legendPosition;
    private String dataLabel;
    private String labelType;
    private String marginLeft;
    private String marginRight;
    private String markerEnabled;
    private List<DimDto> dims;

    public ChartJSONDto() {
    }

    public synchronized List<DimDto> getDims() {
        if (this.dims == null) {
            this.dims = new ArrayList();
            if (this.getXcol() != null && this.getXcol().getId() != null) {
                this.dims.add(this.getXcol());
            }

            if (this.getScol() != null && this.getScol().getId() != null) {
                this.dims.add(this.getScol());
            }

            if (this.getParams() != null && this.getParams().size() > 0) {
                this.dims.addAll(this.getParams());
            }

            return this.dims;
        } else {
            return this.dims;
        }
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DimDto> getParams() {
        return this.params;
    }

    public void setParams(List<DimDto> params) {
        this.params = params;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public DimDto getXcol() {
        return this.xcol;
    }

    public void setXcol(DimDto xcol) {
        this.xcol = xcol;
    }

    public DimDto getScol() {
        return this.scol;
    }

    public void setScol(DimDto scol) {
        this.scol = scol;
    }

    public DimDto getYcol() {
        return this.ycol;
    }

    public void setYcol(DimDto ycol) {
        this.ycol = ycol;
    }

    public Map<String, Object> getLink() {
        return this.link;
    }

    public void setLink(Map<String, Object> link) {
        this.link = link;
    }

    public LinkAcceptDto getLinkAccept() {
        return this.linkAccept;
    }

    public void setLinkAccept(LinkAcceptDto linkAccept) {
        this.linkAccept = linkAccept;
    }

    public String getMaparea() {
        return this.maparea;
    }

    public void setMaparea(String maparea) {
        this.maparea = maparea;
    }

    public String getMapAreaName() {
        return this.mapAreaName;
    }

    public void setMapAreaName(String mapAreaName) {
        this.mapAreaName = mapAreaName;
    }

    public Integer getTypeIndex() {
        return this.typeIndex;
    }

    public void setTypeIndex(Integer typeIndex) {
        this.typeIndex = typeIndex;
    }

    public Integer getHeight() {
        return this.height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getShowLegend() {
        return this.showLegend;
    }

    public void setShowLegend(String showLegend) {
        this.showLegend = showLegend;
    }

    public String getLegendLayout() {
        return this.legendLayout;
    }

    public void setLegendLayout(String legendLayout) {
        this.legendLayout = legendLayout;
    }

    public String getLegendpos() {
        return this.legendpos;
    }

    public void setLegendpos(String legendpos) {
        this.legendpos = legendpos;
    }

    public String getLegendPosition() {
        return this.legendPosition;
    }

    public void setLegendPosition(String legendPosition) {
        this.legendPosition = legendPosition;
    }

    public String getDataLabel() {
        return this.dataLabel;
    }

    public void setDataLabel(String dataLabel) {
        this.dataLabel = dataLabel;
    }

    public String getMarginLeft() {
        return this.marginLeft;
    }

    public void setMarginLeft(String marginLeft) {
        this.marginLeft = marginLeft;
    }

    public String getMarginRight() {
        return this.marginRight;
    }

    public void setMarginRight(String marginRight) {
        this.marginRight = marginRight;
    }

    public String getMarkerEnabled() {
        return this.markerEnabled;
    }

    public void setMarkerEnabled(String markerEnabled) {
        this.markerEnabled = markerEnabled;
    }

    public String getLabelType() {
        return this.labelType;
    }

    public void setLabelType(String labelType) {
        this.labelType = labelType;
    }
}
