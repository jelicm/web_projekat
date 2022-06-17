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
	},
	mounted() {
		axios.get('rest/users/loggedInManager')
		.then(response => (this.manager = response.data))
	},
	methods: {
        logout: function(){
		axios.get('rest/users/logout')
		.then(manager = {})
		}
	}
});