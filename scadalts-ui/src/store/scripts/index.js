const storeScripts = {
	state: {},

	mutations: {},

	actions: {
		searchScripts({ dispatch }, payload) {
			return dispatch('requestGet', `/scripts/search`);
		},
		runScript({ dispatch }, xid) {
			let url = `/scripts/execute/${xid}`;
			return dispatch('requestPost', {url});
		},
		saveScript({ dispatch }, payload) {
			alert(JSON.stringify(payload))
			let url = `/scripts/save`;
			return dispatch('requestPost', {
				url,
				data: {
					id: payload.id,
    				userId: payload.userId,
    				xid: payload.xid,
    				name: payload.name,
    				script: payload.script,
    				pointsOnContext: payload.pointsOnContext,
    				datapointContext: payload.datapointContext,
    				datasourceContext: payload.datasourceContext
				}
			});
		},
		deleteScript({ dispatch }, id) {
			let url = `/scripts/${id}`;
			return dispatch('requestDelete', url);
		},
	},
	getters: {},
};

export default storeScripts;
