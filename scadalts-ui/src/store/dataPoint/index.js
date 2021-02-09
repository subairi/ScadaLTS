import i18n from '@/i18n';
import { chartRenderersTemplates, eventRenderersTemplates, textRenderesTemplates } from './templates';

const storeDataPoint = {
	state: {
		loggingTypeList: [
			{
				id: 1,
				type: 'ON_CHANGE',
				label: i18n.t('pointEdit.logging.type.change'),
			},
			{ 
				id: 2, 
				type: 'ALL', 
				label: i18n.t('pointEdit.logging.type.all') 
			},
			{ 
				id: 3, 
				type: 'NONE', 
				label: i18n.t('pointEdit.logging.type.never') 
			},
			{
				id: 4,
				type: 'INTERVAL',
				label: i18n.t('pointEdit.logging.type.interval'),
			},
			{
				id: 5,
				type: 'ON_TS_CHANGE',
				label: i18n.t('pointEdit.logging.type.tsChange'),
			},
		],

		//TODO: ADD General Time Selectors 
		//      as it is done with TextRenderer list


		//TODO: ADD i18n translations
		textRenderesList: [
			{ id: 0, label: "Analog"},
			{ id: 1, label: "Binary"},
			{ id: 2, label: "Multistate"},
			{ id: 3, label: "Plain"},
			{ id: 4, label: "Range"},
			{ id: 5, label: "Time"},
		],

		textRenderesTemplates: textRenderesTemplates,

		chartRenderersTemplates: chartRenderersTemplates,

		eventRenderersTemplates: eventRenderersTemplates,
		
	},

	mutations: {},

	actions: {
		getDataPointSimpleFilteredList({ dispatch }, value) {
			return new Promise((resolve) => {
				//MOCK of DATAPOINT UI
				let data = [
					{ name: '1 minute Load', id: 1, xid: 'DP_250372' },
					{ name: '5 minutes Load', id: 2, xid: 'DP_335775' },
					{ name: 'Point', id: 5, xid: 'DP_712779' },
					{ name: 'Numeric', id: 6, xid: 'DP_954927' },
					{ name: 'SysUptime', id: 7, xid: 'DP_305240' },
					{ name: 'TrapTest', id: 10, xid: 'DP_568054' },
				];
				data = data.filter((e) => {
					return (e.name || '').toLowerCase().indexOf((value || '').toLowerCase()) > -1;
				});
				resolve(data);
			});
		},

		getDataPointDetailsMock({ dispatch }, datapointId) {
			return new Promise((resolve) => {
				let data = [
					{ name: '1 minute Load', id: 1, xid: 'DP_250372', description: 'First Point' },
					{
						name: '5 minutes Load',
						id: 2,
						xid: 'DP_335775',
						description: 'Example Point',
					},
					{ name: 'Point', id: 5, xid: 'DP_712779', description: 'Just a point' },
					{ name: 'Numeric', id: 6, xid: 'DP_954927', description: 'Not binary' },
					{ name: 'SysUptime', id: 7, xid: 'DP_305240', description: 'Runtime' },
					{ name: 'TrapTest', id: 10, xid: 'DP_568054', description: 'Not worked' },
				];
				data = data.find(({ id }) => id === Number(datapointId));

				resolve(data);
			});
		},

		getDataPointDetails({ dispatch }, datapointId) {
			return dispatch('requestGet', `/datapoint?id=${datapointId}`);
		},

		getDataPointValue({dispatch}, datapointId) {
			return dispatch('requestGet', `/point_value/getValue/id/${datapointId}`);
		},

		setDataPointValue({dispatch}, payload) {
			return dispatch('requestPost', {
				url: `/point_value/setValue/${payload.xid}/${payload.type}/${payload.value}`,
				data: null,
			});
		}
	},

	getters: {},
};

export default storeDataPoint;
