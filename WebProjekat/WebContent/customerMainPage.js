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
		treninzi: {},
		datumi: {},
		tipovi: {}
	},
	mounted() {
		axios.get('rest/users/loggedInCustomer')
		.then(response => (this.customer = response.data))
		axios.get('rest/users/getTrainingsForCustomer')
		.then(response => (this.treninzi = response.data))
		axios.get('rest/users/getTrainingDates')
		.then(response => (this.datumi = response.data))
		axios.get('rest/users/getSportFacilityType')
		.then(response => (this.tipovi = response.data))
	},
	computed: {
	  treninziSaDatumom() {
	    const treninziSaDatumom = []
	    for (let i = 0, len = this.datumi.length; i < len; i++) {
	      treninziSaDatumom.push({
	        treninzi: this.treninzi[i],
	        datumi: this.datumi[i],
	        tipovi: this.tipovi[i]
	      })
	    }
	    return treninziSaDatumom
	  }
	},
	methods: {
        logout: function(){
		axios.get('rest/users/logout')
		.then(customer = {})
		}
	}
});