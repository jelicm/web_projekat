window.addEventListener( "pageshow", function ( event ) {
  var historyTraversal = event.persisted || 
                         ( typeof window.performance != "undefined" && 
                              window.performance.navigation.type === 2 );
  if ( historyTraversal ) {
    window.location.reload();
  }
});

var app = new Vue({
	el: '#managerMainPage',
	data: {
		manager: {},
		treninzi: {},
		datumi:{}
	},
	mounted() {
		axios.get('rest/users/loggedInManager')
		.then(response => {
			this.manager = response.data
			axios.get('rest/sportFacilities/trainingsForSportFacility/' + this.manager.sportFacility)
			.then(response => {this.treninzi = response.data})
			axios.get('rest/sportFacilities/getTrainingDates')
			.then(response => (this.datumi = response.data))
		
		})
		
	},
	computed: {
	  treninziSaDatumom() {
	    const treninziSaDatumom = []
	    for (let i = 0, len = this.datumi.length; i < len; i++) {
	      treninziSaDatumom.push({
	        treninzi: this.treninzi[i],
	        datumi: this.datumi[i]
	      })
	    }
	    return treninziSaDatumom
	  }
	},
	methods: {
        logout: function(){
		axios.get('rest/users/logout')
		.then(manager = {})
		},
		izmeni: function(tsd){
			axios.get('rest/users/trainingReview/' + tsd.treninzi.name)
		.then(response => {location.href=response.data} )
		}
	}
});