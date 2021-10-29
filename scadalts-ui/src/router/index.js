import Vue from 'vue';
import Router from 'vue-router';

import About from '../views/About';
import LoginPage from '../views/LoginPage';

import alarmRoutes from './routes-alarms';
import exampleRoutes from './routes-examples';
import systemRoutes from './routes-system';
import userRoutes from './routes-users';
import dataSourceRoutes from './routes-datasources';
import eventRoutes from './routes-events';

import GraphicalView from '../views/GraphicalViews';
import Reports from '../views/Reports';
import SynopticPanelMenu from '../views/SynopticPanel/SynopticPanelMenu';
import SynopticPanelItem from '../views/SynopticPanel/SynopticPanelItem';
import WatchList from '../views/WatchList';
import WatchListItem from '../views/WatchList/WatchListItem';

import store from '../store/index';

Vue.use(Router);

const routing = new Router({
	mode: 'hash',
	base: process.env.BASE_URL,
	routes: [
		
		{
			path: '/login',
			name: 'login',
			component: LoginPage,
		},
		{
			path: '/about',
			name: 'about',
			component: About,
			meta: {
				requiresAuth: true
			},
		},
        {
            path: '/graphical-view',
			name: 'graphical-view',
			component: GraphicalView,
			meta: {
                requiresAuth: true
            }

        },
		{
			path: '/synoptic-panel',
			name: 'synoptic-panel',
			component: SynopticPanelMenu,
			children: [
				{
					path: ':id',
					component: SynopticPanelItem,
				},
			],
		},
        {
            path: '/reports',
			name: 'reports',
			component: Reports,
			meta: {
                requiresAuth: true
            }

        },
		{
			path: '/watch-list',
			name: 'watch-list',
			component: WatchList,
			children: [
				{
					path: ':id',
					component: WatchListItem,
				}
			]
		},
        ...alarmRoutes,
        ...dataSourceRoutes,
        ...eventRoutes,
        ...userRoutes,
        ...systemRoutes,
		...exampleRoutes,		

	],
});

routing.beforeEach((to, from, next) => {
	if (to.meta.requiresAuth) {
		if (!store.state.loggedUser) {
			store.dispatch('getUserInfo')
				.catch(() => {
					next({ name: 'login' });
				})
		}
	}
	next();
})

export default routing;