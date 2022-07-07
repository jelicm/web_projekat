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
		objekti: {}, 
	},
	mounted() {
		axios.get('rest/users/loggedInAdmin')
		.then(response => (this.admin = response.data))
		axios.get('rest/sportFacilities')
		.then(response => (this.objekti = response.data))
	},
	methods: {
        logout: function(){
		axios.get('rest/users/logout')
		.then(admin = {})
		},
		obrisi: function(o){
			
			axios.delete('rest/sportFacilities/deleteSportFacility/' + o.name)
			.then(response => {
				this.objekti = this.objekti.filter(ob => ob.name !== o.name)
			});
		}
	}
});