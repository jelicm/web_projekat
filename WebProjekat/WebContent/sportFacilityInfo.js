var app = new Vue({
    el: '#sportFacilityInfo',
    data:{
        sportFacility: null,
        
    },
	mounted() {
		axios.get('rest/sportFacilities/reviewed')
		.then(response => {this.sportFacility = response.data})
	}
})