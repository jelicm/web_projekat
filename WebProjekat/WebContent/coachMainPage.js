window.addEventListener( "pageshow", function ( event ) {
  var historyTraversal = event.persisted || 
                         ( typeof window.performance != "undefined" && 
                              window.performance.navigation.type === 2 );
  if ( historyTraversal ) {
    window.location.reload();
  }
});

var app = new Vue({
	el: '#coachMainPage',
	data: {
		coach: {},
	},
	mounted() {
		axios.get('rest/users/loggedInCoach')
		.then(response => (this.coach = response.data))
	},
	methods: {
        logout: function(){
		axios.get('rest/users/logout')
		.then(coach = {})
		}
	}
});