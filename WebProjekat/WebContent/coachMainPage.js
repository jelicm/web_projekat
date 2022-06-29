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
		personalni: {},
		datumiPersonalni: {},
		tipoviPersonalni: {},
		grupni: {},
		datumiGrupni: {},
		tipoviGrupni: {}
	},
	mounted() {
		axios.get('rest/users/loggedInCoach')
		.then(response => (this.coach = response.data))
		axios.get('rest/users/getPersonalTrainingsForCoach')
		.then(response => (this.personalni = response.data))
		axios.get('rest/users/getGroupTrainingsForCoach')
		.then(response => (this.grupni = response.data))
		axios.get('rest/users/getPersonalTrainingDatesForCoach')
		.then(response => (this.datumiPersonalni = response.data))
		axios.get('rest/users/getGroupTrainingDatesForCoach')
		.then(response => (this.datumiGrupni = response.data))
		axios.get('rest/users/getSportFacilityTypeForPersonal')
		.then(response => (this.tipoviPersonalni = response.data))
		axios.get('rest/users/getSportFacilityTypeForGroup')
		.then(response => (this.tipoviGrupni = response.data))
	},
	computed: {
	  personalniTreninzi() {
	    const personalniTreninzi = []
	    for (let i = 0, len = this.datumiPersonalni.length; i < len; i++) {
	      personalniTreninzi.push({
	        treninzi: this.personalni[i],
	        datumi: this.datumiPersonalni[i],
	        tipovi: this.tipoviPersonalni[i]
	      })
	    }
	    return personalniTreninzi
	  },
	  grupniTreninzi() {
	    const grupniTreninzi = []
	    for (let i = 0, len = this.datumiPersonalni.length; i < len; i++) {
	      grupniTreninzi.push({
	        treninzi: this.grupni[i],
	        datumi: this.datumiGrupni[i],
	        tipovi: this.tipoviGrupni[i]
	      })
	    }
	    return grupniTreninzi
	  }
	},
	methods: {
        logout: function(){
		axios.get('rest/users/logout')
		.then(coach = {})
		}
	}
});