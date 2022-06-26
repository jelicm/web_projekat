var app = new Vue({
    el: '#sportFacility',
    data:{
        sportFacility: null,
        coachesForSportFacility: {},
        customersForSportFacility: {}
    },
	mounted() {
		axios.get('rest/users/managerSportFacility')
		.then(response => {this.sportFacility = response.data})
		axios.get('rest/users/coachesForSportFacility')
		.then(response => {this.coachesForSportFacility = response.data})
		axios.get('rest/users/customersForSportFacility')
		.then(response => {this.customersForSportFacility = response.data})
	}
})