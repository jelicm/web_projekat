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
	},
	mounted() {
		axios.get('rest/users/loggedInManager')
		.then(response => {
			this.manager = response.data
			axios.get('rest/sportFacilities/trainingsForSportFacility/' + this.manager.sportFacility)
			.then(response => {this.treninzi = response.data})
		
		})
		
	},
	methods: {
        logout: function(){
		axios.get('rest/users/logout')
		.then(manager = {})
		},
		izmeni: function(t){
			axios.get('rest/users/trainingReview/' + t.name)
		.then(response => {location.href=response.data} )
		}
	}
});