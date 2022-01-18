//TODO: Separate API with minimal required information for Table 
//      and seperate API for other data sources with more details

export const datasourceDetailsMocks = [
    {
        id: 0,
        xid: "MOCK_DS_01312212",
        enabled: true,
        name: "DataSourceMock VSD - 01",
        type: 1,
        connection: '5 minutes',
        description: 'Mocked VDS',
        activeEvents: 0,
        updatePeriod: 5,
        updatePeriodType: 1,
    },
    {
        id: 1,
        xid: "MOCK_DS_0154151",
        enabled: false,
        name: "DataSourceMock VSD - 02",
        type: 1,
        connection: '30 seconds',
        description: 'Mocked Disabled VDS',
        activeEvents: 2,
        updatePeriod: 5,
        updatePeriodType: 1,
    },
    {
        id: 2,
        xid: "MOCK_DS_01415115",
        enabled: true,
        name: "DataSourceMock SNMP - 01",
        type: 5,
        connection: 'localhost',
        description: 'Mocked SNMP',
        activeEvents: 1,
        updatePeriod: 5,
        updatePeriodType: 1,

        host: 'localhost',
        port:'161',
        snmpVersion: '1',
        community: 'public',
        engineId: '',
        contextEngineId: '',
        contextName: '',
        securityName: '',
        authProtocol: '',
        authPassphrase: '',
        privProtocol: '',
        privPassphrase: '',
        securityLevel: '',
        retries: 2,
        timeout: 1000,
        trapEnabled: true,
        trapPort: 162,
        localAddress: '',
    },
    {
        id: 3,
        xid: "MOCK_DS_115",
        enabled: true,
        name: "DataSourceMock ModBusIP - 01",
        type: 3,
        connection: 'localhost',
        description: 'Mocked SNMP',
        activeEvents: 0,
        updatePeriod: 5,
        updatePeriodType: 1,

        quantize: true,
        timeout: 500,
        retries: 3,
        contiguousBatches: true,
        createSlaveMonitorPoints: false,
        maxReadBitCount: 2000,
        maxReadRegisterCount: 125,
        maxWriteRegisterCount: 120,


        transportType: 'localhost',
        host: 'localhost',
        port:'502',
        encapsulated: false,
        createSockerMonitorPort: false,
        
    }
]