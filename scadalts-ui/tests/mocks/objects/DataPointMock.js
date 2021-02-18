export const dataPointMock = {
	"id": 1,
	"xid": "DP_1111",
	"name": "Test Numeric DP",
	"description": "Example description for testing",
	"dataSourceId": 1,
	"deviceName": "Example DataSource",
	"enabled": true,
	"pointFolderId": 0,
	"loggingType": 4,
	"intervalLoggingPeriodType": 2,
	"intervalLoggingPeriod": 15,
	"intervalLoggingType": 4,
	"tolerance": 0.0,
	"purgeType": 7,
	"purgePeriod": 1,
	"eventTextRenderer": {
		"rangeEventValues": [
			{
				"from": 0.0,
				"to": 1.0,
				"shortText": "S",
				"longText": "Short",
				"colour": "#0000ff"
			},
			{ "from": 2.0, "to": 3.0, "shortText": "L", "longText": "Long", "colour": null }
		],
		"typeName": "eventTextRendererRange",
		"def": {
			"id": 0,
			"name": "eventTextRendererRange",
			"exportName": "EVENT_RANGE",
			"nameKey": "textRenderer.range",
			"supportedDataTypes": [3]
		},
		"colour": null,
		"metaShortText": null,
		"metaLongText": null
	},
	"textRenderer": {
		"format": "0.0",
		"conversionExponent": 1,
		"typeName": "textRendererTime",
		"def": {
			"id": 0,
			"name": "textRendererTime",
			"exportName": "TIME",
			"nameKey": "textRenderer.time",
			"supportedDataTypes": [3]
		},
		"colour": null,
		"metaText": null
	},
	"chartRenderer": null,
	"eventDetectors": [
		{
			"id": 7,
			"xid": "PED_491167",
			"alias": "",
			"detectorType": 1,
			"alarmLevel": 0,
			"limit": 0.0,
			"duration": 0,
			"durationType": 1,
			"binaryState": false,
			"multistateState": 0,
			"changeCount": 2,
			"alphanumericState": null,
			"weight": 0.0,
			"rtnApplicable": true,
			"eventDetectorKey": "P7",
			"typeKey": "event.audit.pointEventDetector",
			"def": {
				"id": 1,
				"name": null,
				"exportName": null,
				"nameKey": "pointEdit.detectors.highLimit",
				"supportedDataTypes": [3]
			},
			"durationDescription": null,
			"eventType": {
				"typeId": 1,
				"typeRef1": 12,
				"typeRef2": 7,
				"description": { "key": "event.detectorVo.highLimit", "args": ["0"] },
				"handlers": null,
				"alarmLevel": 0,
				"eventDetectorKey": "P7"
			},
			"description": { "key": "event.detectorVo.highLimit", "args": ["0"] }
		},
	],
	"comments": [],
	"defaultCacheSize": 1,
	"discardExtremeValues": false,
	"discardLowLimit": -1.7976931348623157e308,
	"discardHighLimit": 1.7976931348623157e308,
	"engineeringUnits": 0,
	"chartColour": null,
	"pointLocator": {
		"dataTypeId": 3,
		"changeTypeId": 6,
		"settable": true,
		"alternateBooleanChange": {
			"startValue": "true",
			"description": { "key": "dsEdit.virtual.changeType.alternate", "args": [] }
		},
		"brownianChange": {
			"startValue": "",
			"min": 0.0,
			"max": 0.0,
			"maxChange": 0.0,
			"description": { "key": "dsEdit.virtual.changeType.brownian", "args": [] }
		},
		"incrementAnalogChange": {
			"startValue": "",
			"min": 0.0,
			"max": 0.0,
			"change": 0.0,
			"roll": false,
			"description": { "key": "dsEdit.virtual.changeType.increment", "args": [] }
		},
		"incrementMultistateChange": {
			"startValue": "",
			"values": [],
			"roll": false,
			"description": { "key": "dsEdit.virtual.changeType.increment", "args": [] }
		},
		"noChange": {
			"startValue": "",
			"description": { "key": "dsEdit.virtual.changeType.noChange", "args": [] }
		},
		"randomAnalogChange": {
			"startValue": "1",
			"min": 0.0,
			"max": 15.0,
			"description": { "key": "dsEdit.virtual.changeType.random", "args": [] }
		},
		"randomBooleanChange": {
			"startValue": "true",
			"description": { "key": "dsEdit.virtual.changeType.random", "args": [] }
		},
		"randomMultistateChange": {
			"startValue": "",
			"values": [],
			"description": { "key": "dsEdit.virtual.changeType.random", "args": [] }
		},
		"analogAttractorChange": {
			"startValue": "",
			"maxChange": 0.0,
			"volatility": 0.0,
			"attractionPointId": 12,
			"description": { "key": "dsEdit.virtual.changeType.attractor", "args": [] }
		},
		"configurationDescription": { "key": "dsEdit.virtual.changeType.random", "args": [] },
		"dataTypeMessage": { "key": "common.dataTypes.numeric", "args": [] },
		"relinquishable": false,
		"dataPointSaveHandler": null
	},
	"dataSourceTypeId": 1,
	"dataSourceName": "Properties DS",
	"dataSourceXid": "DS_196882",
	"settable": false,
	"extendedName": "Properties DS - Numeric DP",
	"typeKey": "event.audit.dataPoint",
	"dataTypeMessage": { "key": "common.dataTypes.numeric", "args": [] },
	"configurationDescription": { "key": "dsEdit.virtual.changeType.random", "args": [] },
	"new": false
}

export default dataPointMock