window.addEventListener( "pageshow", function ( event ) {
  var historyTraversal = event.persisted || 
                         ( typeof window.performance != "undefined" && 
                              window.performance.navigation.type === 2 );
  if ( historyTraversal ) {
    window.location.reload();
  }
});

var app = new Vue({
	el: '#customerMainPage',
	data: {
		customer: {},
	},
	mounted() {
		axios.get('rest/users/loggedInCustomer')
		.then(response => (this.customer = response.data))
	},
	methods: {
        logout: function(){
		axios.get('rest/users/logout')
		.then(customer = {})
		}
	}
});