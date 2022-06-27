var app = new Vue({
    el: '#sportFacilityInfo',
    data:{
        sportFacility: null,
		treninzi: {}
        
    },
	mounted() {
		axios.get('rest/sportFacilities/reviewed')
		.then(response => {
			this.sportFacility = response.data
			axios.get('rest/sportFacilities/trainingsForSportFacility/' + this.sportFacility.name)
			.then(response => {this.treninzi = response.data})
		})
		
	}
})