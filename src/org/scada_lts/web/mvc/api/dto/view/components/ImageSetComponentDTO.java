package org.scada_lts.web.mvc.api.dto.view.components;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.serotonin.mango.Common;
import com.serotonin.mango.view.ImageSet;
import com.serotonin.mango.view.component.deserializer.ImageSetComponentDeserializer;

@JsonDeserialize(using = ImageSetComponentDeserializer.class)
public class ImageSetComponentDTO extends PointComponentDTO {

    private String imageSetId;
    private Boolean displayText;

    public ImageSetComponentDTO() {
    }

    public ImageSetComponentDTO(Integer index, String idSuffix, Integer x, Integer y, Integer z, String typeName, String dataPointXid, String nameOverride, Boolean settableOverride, String bkgdColorOverride, Boolean displayControls, String imageSetId, Boolean displayText) {
        super(index, idSuffix, x, y, z, typeName, dataPointXid, nameOverride, settableOverride, bkgdColorOverride, displayControls);
        this.imageSetId = imageSetId;
        this.displayText = displayText;
    }

    public String getImageSetId() {
        return imageSetId;
    }

    public void setImageSetId(String imageSetId) {
        this.imageSetId = imageSetId;
    }

    public Boolean getDisplayText() {
        return displayText;
    }

    public void setDisplayText(Boolean displayText) {
        this.displayText = displayText;
    }

    protected ImageSet getImageSet() {
        return Common.ctx.getImageSets().stream().filter(i -> i.getId().equals(imageSetId)).findAny().orElse(null);
    }
}
