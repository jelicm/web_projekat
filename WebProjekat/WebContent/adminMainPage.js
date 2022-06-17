window.addEventListener( "pageshow", function ( event ) {
  var historyTraversal = event.persisted || 
                         ( typeof window.performance != "undefined" && 
                              window.performance.navigation.type === 2 );
  if ( historyTraversal ) {
    window.location.reload();
  }
});

var app = new Vue({
	el: '#adminMainPage',
	data: {
		admin: {},
	},
	mounted() {
		axios.get('rest/users/loggedInAdmin')
		.then(response => (this.admin = response.data))
	},
	methods: {
        logout: function(){
		axios.get('rest/users/logout')
		.then(admin = {})
		}
	}
});