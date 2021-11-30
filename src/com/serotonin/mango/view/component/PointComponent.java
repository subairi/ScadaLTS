/*
    Mango - Open Source M2M - http://mango.serotoninsoftware.com
    Copyright (C) 2006-2011 Serotonin Software Technologies Inc.
    @author Matthew Lohbihler
    
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.serotonin.mango.view.component;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.serotonin.json.JsonException;
import com.serotonin.json.JsonObject;
import com.serotonin.json.JsonReader;
import com.serotonin.json.JsonRemoteProperty;
import com.serotonin.mango.rt.dataImage.PointValueTime;
import com.serotonin.mango.vo.DataPointVO;
import com.serotonin.mango.vo.User;
import com.serotonin.mango.vo.permission.Permissions;
import com.serotonin.util.SerializationHelper;
import com.serotonin.util.StringUtils;
import com.serotonin.web.i18n.LocalizableMessage;

/**
 * @author Matthew Lohbihler
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "typeName"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImageSetComponent.class, name = "BINARY_GRAPHIC"),
        @JsonSubTypes.Type(value = ImageSetComponent.class, name = "ANALOG_GRAPHIC"),
        @JsonSubTypes.Type(value = ImageSetComponent.class, name = "MULTISTATE_GRAPHIC"),
        @JsonSubTypes.Type(value = ScriptComponent.class, name = "BUTTON"),
        @JsonSubTypes.Type(value = DynamicGraphicComponent.class, name = "DYNAMIC_GRAPHIC"),
        @JsonSubTypes.Type(value = SimplePointComponent.class, name = "ENHANCED_POINT"),
        @JsonSubTypes.Type(value = SimplePointComponent.class, name = "SIMPLE"),
        @JsonSubTypes.Type(value = ScriptComponent.class, name = "SCRIPT"),
        @JsonSubTypes.Type(value = SimpleImageComponent.class, name = "SIMPLE_IMAGE"),
        @JsonSubTypes.Type(value = ThumbnailComponent.class, name = "THUMBNAIL")
})
abstract public class PointComponent extends ViewComponent {

    @JsonDeserialize(using = DataPointFieldDeserializer.class)
    @JsonProperty("dataPointXid")
    private DataPointVO dataPoint;
    @JsonRemoteProperty
    private String nameOverride;
    @JsonRemoteProperty
    private boolean settableOverride;
    @JsonRemoteProperty
    private String bkgdColorOverride;
    @JsonRemoteProperty
    private boolean displayControls;

    // Runtime attributes
    private boolean valid;
    private boolean visible;

    @Override
    public boolean isPointComponent() {
        return true;
    }

    abstract public void addDataToModel(Map<String, Object> model, PointValueTime pointValue);

    abstract public String snippetName();

    @Override
    public void validateDataPoint(User user, boolean makeReadOnly) {
        if (dataPoint == null) {
            valid = false;
            visible = false;
            settableOverride = !makeReadOnly;
        } else {
            visible = Permissions.hasDataPointReadPermission(user, dataPoint);
            valid = definition().supports(dataPoint.getPointLocator().getDataTypeId());
            settableOverride = !makeReadOnly && Permissions.hasDataPointSetPermission(user, dataPoint);
        }
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public boolean isVisible() {
        return visible;
    }

    @Override
    public boolean containsValidVisibleDataPoint(int dataPointId) {
        if (!valid || !visible)
            return false;

        return dataPoint.getId() == dataPointId;
    }

    public int[] getSupportedDataTypes() {
        return definition().getSupportedDataTypes();
    }

    public String getTypeName() {
        return definition().getName();
    }

    public LocalizableMessage getDisplayName() {
        return new LocalizableMessage(definition().getNameKey());
    }

    public String getName() {
        if (!StringUtils.isEmpty(nameOverride))
            return nameOverride;
        if (dataPoint == null)
            return "(unknown)";
        return dataPoint.getName();
    }

    public boolean isSettable() {
        if (dataPoint == null)
            return false;
        if (!dataPoint.getPointLocator().isSettable())
            return false;
        return settableOverride;
    }

    public boolean isChartRenderer() {
        if (dataPoint == null)
            return false;
        return dataPoint.getChartRenderer() != null;
    }

    public DataPointVO tgetDataPoint() {
        return dataPoint;
    }

    public void tsetDataPoint(DataPointVO dataPoint) {
        this.dataPoint = dataPoint;
    }

    public int getDataPointId() {
        if (dataPoint == null)
            return 0;
        return dataPoint.getId();
    }

    public String getNameOverride() {
        return nameOverride;
    }

    public void setNameOverride(String nameOverride) {
        this.nameOverride = nameOverride;
    }

    public boolean isSettableOverride() {
        return settableOverride;
    }

    public void setSettableOverride(boolean settableOverride) {
        this.settableOverride = settableOverride;
    }

    public String getBkgdColorOverride() {
        return bkgdColorOverride;
    }

    public void setBkgdColorOverride(String bkgdColorOverride) {
        this.bkgdColorOverride = bkgdColorOverride;
    }

    public boolean isDisplayControls() {
        return displayControls;
    }

    public void setDisplayControls(boolean displayControls) {
        this.displayControls = displayControls;
    }

    //
    // /
    // / Serialization
    // /
    //
    private static final long serialVersionUID = -1;
    private static final int version = 1;

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(version);

        writeDataPoint(out, dataPoint);
        SerializationHelper.writeSafeUTF(out, nameOverride);
        out.writeBoolean(settableOverride);
        SerializationHelper.writeSafeUTF(out, bkgdColorOverride);
        out.writeBoolean(displayControls);
    }

    private void readObject(ObjectInputStream in) throws IOException {
        int ver = in.readInt();

        // Switch on the version of the class so that version changes can be elegantly handled.
        if (ver == 1) {
            dataPoint = readDataPoint(in);
            nameOverride = SerializationHelper.readSafeUTF(in);
            settableOverride = in.readBoolean();
            bkgdColorOverride = SerializationHelper.readSafeUTF(in);
            displayControls = in.readBoolean();
        }
    }

    @Override
    public void jsonDeserialize(JsonReader reader, JsonObject json) throws JsonException {
        super.jsonDeserialize(reader, json);
        jsonDeserializeDataPoint(json.getValue("dataPointXid"), this);
    }

    @Override
    public void jsonSerialize(Map<String, Object> map) {
        super.jsonSerialize(map);
        jsonSerializeDataPoint(map, "dataPointXid", this);
    }
}
