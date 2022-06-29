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
		tipoviGrupni: {},
		naziv1: '',
		minCena1: 0,
		maxCena1: 0,
		datumPocetak1: '',
		datumKraj1: '',
		naziv2: '',
		minCena2: 0,
		maxCena2: 0,
		datumPocetak2: '',
		datumKraj2: '',
		
		personalniTreninziFilter: [],
		personalniTreninzi: [],
		grupniTreninzi: [],
		grupniTreninziFilter:[],
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
		.then(response => {
			this.tipoviPersonalni = response.data;
			for (let i = 0, len = this.datumiPersonalni.length; i < len; i++) {
		      this.personalniTreninzi.push({
		        treninzi: this.personalni[i],
		        datumi: this.datumiPersonalni[i],
		        tipovi: this.tipoviPersonalni[i]
		      })
			}
			this.personalniTreninziFilter = this.personalniTreninzi;
		
		})
		axios.get('rest/users/getSportFacilityTypeForGroup')
		.then(response => {
			
			this.tipoviGrupni = response.data
			for (let i = 0, len = this.datumiGrupni.length; i < len; i++) {
			    this.grupniTreninzi.push({
			        treninzi: this.grupni[i],
			        datumi: this.datumiGrupni[i],
			        tipovi: this.tipoviGrupni[i]
			      })
		    }
			this.grupniTreninziFilter = this.grupniTreninzi
		})
	},
	computed: {
	  /*personalniTreninzi() {
	    const personalniTreninzi = []
	    for (let i = 0, len = this.datumiPersonalni.length; i < len; i++) {
	      personalniTreninzi.push({
	        treninzi: this.personalni[i],
	        datumi: this.datumiPersonalni[i],
	        tipovi: this.tipoviPersonalni[i]
	      })
	    }
		this.personalniTreninziFilter = personalniTreninzi
	    return personalniTreninzi
	  },*/
	  /*grupniTreninzi() {
	    const grupniTreninzi = []
	    for (let i = 0, len = this.datumiGrupni.length; i < len; i++) {
	      grupniTreninzi.push({
	        treninzi: this.grupni[i],
	        datumi: this.datumiGrupni[i],
	        tipovi: this.tipoviGrupni[i]
	      })
	    }
	    return grupniTreninzi
	  }*/
	},
	methods: {
        logout: function(){
		axios.get('rest/users/logout')
		.then(coach = {})
		},
		otkazi: function(pt){
		axios.get('rest/users/cancelTraining/' + pt.treninzi.name)
        .then(response => {
            location.href=response.data 
        })
        .catch(function(){
            alert("Trening je za manje od 2 dana, ne moze se otkazati!")
        })	
        },
		pretragaNazivPersonalni: function(naziv1){
			this.personalniTreninziFilter = this.personalniTreninzi.filter(o => o.treninzi.sportFacility.toLowerCase().includes(naziv1.toLowerCase()));
			this.minCena1 = 0;
			this.maxCena1 = 0;
			this.datumPocetak1 = '';
			this.datumKraj1 = '';
		},
		
		pretragaNazivGrupni: function(naziv2){
			this.grupniTreninziFilter = this.grupniTreninzi.filter(o => o.treninzi.sportFacility.toLowerCase().includes(naziv2.toLowerCase()));
			this.minCena2 = 0;
			this.maxCena2 = 0;
			this.datumPocetak2 = '';
			this.datumKraj2 = '';
		},
		
		ponistiPretraguPersonalni: function(){
			this.personalniTreninziFilter = this.personalniTreninzi;
			this.naziv1 = '';
			this.minCena1 = 0;
			this.maxCena1 = 0;
			this.datumPocetak1 = '';
			this.datumKraj1 = '';
		},
		
		ponistiPretraguGrupni: function(){
			this.grupniTreninziFilter = this.grupniTreninzi;
			this.naziv2 = '';
			this.minCena2 = 0;
			this.maxCena2 = 0;
			this.datumPocetak2 = '';
			this.datumKraj2 = '';
		},
		
		pretragaDatumPersonalni: function(datumPocetak1, datumKraj1){
			dp = datumPocetak1.split('-')
			dk = datumKraj1.split('-')
			
			if(parseInt(dp[0]) == parseInt(dk[0]) && parseInt(dp[1]) == parseInt(dk[1]) && parseInt(dp[2]) > parseInt(dk[2])){
				alert("Niste dobro uneli kriterijume pretrage po datumu!")
				this.datumPocetak1 = '';
				this.datumKraj1 = '';
			}
			else if(parseInt(dp[0]) == parseInt(dk[0]) && parseInt(dp[1]) > parseInt(dk[1])){
				alert("Niste dobro uneli kriterijume pretrage po datumu!")
				this.datumPocetak1 = '';
				this.datumKraj1 = '';
			}
			else if(parseInt(dp[0]) > parseInt(dk[0])){
				alert("Niste dobro uneli kriterijume pretrage po datumu!")
				this.datumPocetak1 = '';
				this.datumKraj1 = '';
			}

			else {
				
				this.personalniTreninziFilter = this.personalniTreninzi.filter(o => ((parseInt(o.datumi.split(' ')[0].split('.')[2]) > parseInt(dp[0]))||
				 (parseInt(o.datumi.split(' ')[0].split('.')[2]) == parseInt(dp[0]) && parseInt(o.datumi.split(' ')[0].split('.')[1]) > parseInt(dp[1]))|| 
				 (parseInt(o.datumi.split(' ')[0].split('.')[2]) == parseInt(dp[0]) && parseInt(o.datumi.split(' ')[0].split('.')[1]) == parseInt(dp[1]) && parseInt(o.datumi.split(' ')[0].split('.')[0]) >= parseInt(dp[2])))
				 && ((parseInt(o.datumi.split(' ')[0].split('.')[2]) < parseInt(dk[0])) || 
					(parseInt(o.datumi.split(' ')[0].split('.')[2]) == parseInt(dk[0]) && parseInt(o.datumi.split(' ')[0].split('.')[1]) < parseInt(dk[1])) || 
					(parseInt(o.datumi.split(' ')[0].split('.')[2]) == parseInt(dk[0]) && parseInt(o.datumi.split(' ')[0].split('.')[1]) == parseInt(dk[1]) && parseInt(o.datumi.split(' ')[0].split('.')[0]) <= parseInt(dk[2]))))

				this.naziv1 = '';
				this.minCena1 = 0;
				this.maxCena1 = 0;
				
			}
			
		},
		
		pretragaDatumGrupni: function(datumPocetak2, datumKraj2){
			dp = datumPocetak2.split('-')
			dk = datumKraj2.split('-')
			
			if(parseInt(dp[0]) == parseInt(dk[0]) && parseInt(dp[1]) == parseInt(dk[1]) && parseInt(dp[2]) > parseInt(dk[2])){
				alert("Niste dobro uneli kriterijume pretrage po datumu!")
				this.datumPocetak2 = '';
				this.datumKraj2 = '';
			}
			else if(parseInt(dp[0]) == parseInt(dk[0]) && parseInt(dp[1]) > parseInt(dk[1])){
				alert("Niste dobro uneli kriterijume pretrage po datumu!")
				this.datumPocetak2 = '';
				this.datumKraj2 = '';
			}
			else if(parseInt(dp[0]) > parseInt(dk[0])){
				alert("Niste dobro uneli kriterijume pretrage po datumu!")
				this.datumPocetak2 = '';
				this.datumKraj2 = '';
			}

			else {
				
				this.grupniTreninziFilter = this.grupniTreninzi.filter(o => ((parseInt(o.datumi.split(' ')[0].split('.')[2]) > parseInt(dp[0]))||
				 (parseInt(o.datumi.split(' ')[0].split('.')[2]) == parseInt(dp[0]) && parseInt(o.datumi.split(' ')[0].split('.')[1]) > parseInt(dp[1]))|| 
				 (parseInt(o.datumi.split(' ')[0].split('.')[2]) == parseInt(dp[0]) && parseInt(o.datumi.split(' ')[0].split('.')[1]) == parseInt(dp[1]) && parseInt(o.datumi.split(' ')[0].split('.')[0]) >= parseInt(dp[2])))
				 && ((parseInt(o.datumi.split(' ')[0].split('.')[2]) < parseInt(dk[0])) || 
					(parseInt(o.datumi.split(' ')[0].split('.')[2]) == parseInt(dk[0]) && parseInt(o.datumi.split(' ')[0].split('.')[1]) < parseInt(dk[1])) || 
					(parseInt(o.datumi.split(' ')[0].split('.')[2]) == parseInt(dk[0]) && parseInt(o.datumi.split(' ')[0].split('.')[1]) == parseInt(dk[1]) && parseInt(o.datumi.split(' ')[0].split('.')[0]) <= parseInt(dk[2]))))

				this.naziv2 = '';
				this.minCena2 = 0;
				this.maxCena2 = 0;
				
			}
			
		},
		
		
		pretragaCenaPersonalni: function(minCena1, maxCena1){
			
			if(parseInt(minCena1) > parseInt(maxCena1)){
				alert("Niste dobro uneli kriterijume pretrage po ceni!")
				this.minCena1 = 0;
				this.maxCena1 = 0;
			}
				
			else {
				
				this.personalniTreninziFilter = this.personalniTreninzi.filter(o => o.treninzi.price >= minCena1 && o.treninzi.price <= maxCena1);
				this.naziv1 = '';
				this.datumPocetak1 = '';
				this.datumKraj1 = '';
			}
			
		},
		
		pretragaCenaGrupni: function(minCena2, maxCena2){
			
			if(parseInt(minCena2) > parseInt(maxCena2)){
				alert("Niste dobro uneli kriterijume pretrage po ceni!")
				this.minCena2 = 0;
				this.maxCena2 = 0;
			}
				
			else {
				
				this.grupniTreninziFilter = this.grupniTreninzi.filter(o => o.treninzi.price >= minCena2 && o.treninzi.price <= maxCena2);
				this.naziv2 = '';
				this.datumPocetak2 = '';
				this.datumKraj2 = '';
			}
			
		},
	}
});