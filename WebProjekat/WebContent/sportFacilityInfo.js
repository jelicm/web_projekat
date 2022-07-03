var app = new Vue({
    el: '#sportFacilityInfo',
    data:{
        sportFacility: null,
		treninzi: {},
		komentari: {}
    },
	mounted() {
		axios.get('rest/sportFacilities/reviewed')
		.then(response => {
			this.sportFacility = response.data
			axios.get('rest/sportFacilities/trainingsForSportFacility/' + this.sportFacility.name)
			.then(response => {this.treninzi = response.data})
			axios.get('rest/comments/approvedCommentsForFacility/' + this.sportFacility.name)
			.then(response => {this.komentari = response.data})
		})
		
	}
})