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
		sortiranoNaziv1 : 0,
		sortiranoCena1 : 0,
		sortiranoDatum1: 0,
		sortiranoNaziv2: 0,
		sortiranoCena2: 0,
		sortiranoDatum2: 0,
		tipo1: '',
		tipo2: '',
		tipoviObjekata: {},
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
		axios.get("rest/enums/facilityType")
			 .then((response) => {this.tipoviObjekata = response.data});
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
		
		sortNaziv1: function(){
			if(this.sortiranoNaziv1 === 0)
			{
				this.personalniTreninziFilter = this.personalniTreninziFilter.sort((a,b) => (a.treninzi.sportFacility > b.treninzi.sportFacility) ? 1 : ((a.treninzi.sportFacility < b.treninzi.sportFacility) ? -1 : 0));
				this.sortiranoNaziv1 = 1
				this.sortiranoCena1 = 0
				this.sortiranoDatum1 =0
			}
			else
			{
				this.personalniTreninziFilter.reverse()
				this.sortiranoNaziv1 = 0
			}
				
		},
		
		sortCena1: function(){
			if(this.sortiranoCena1 === 0)
			{
				this.personalniTreninziFilter = this.personalniTreninziFilter.sort((a,b) => (a.treninzi.price > b.treninzi.price) ? 1 : ((a.treninzi.price < b.treninzi.price) ? -1 : 0));
				this.sortiranoNaziv1 = 0
				this.sortiranoCena1 = 1
				this.sortiranoDatum1 = 0
			}
			else
			{
				this.personalniTreninziFilter.reverse()
				this.sortiranoCena1 = 0
			}
				
		},
		
		sortDatum1: function(){
			if(this.sortiranoDatum1 === 0)
			{
				this.personalniTreninziFilter = this.personalniTreninziFilter.sort((a,b) => {
					
					const pa = a.datumi.split(" ")[0].split(".");
					const pb = b.datumi.split(" ")[0].split(".");
					if(parseInt(pa[2]) > parseInt(pb[2]))
						return 1;
					else if(parseInt(pa[2]) < parseInt(pb[2]))
						return -1;
					else if (parseInt(pa[1]) > parseInt(pb[1]))
						return 1;
					else if(parseInt(pa[1]) < parseInt(pb[1]))
						return -1;
					else if (parseInt(pa[0]) > parseInt(pb[0]))
						return 1;
					else if(parseInt(pa[0]) < parseInt(pb[0]))
						return -1;
					else 
					{
						if(a.datumi.split(" ")[1].split(":")[0] > b.datumi.split(" ")[1].split(":")[0])
							return 1;
						if(a.datumi.split(" ")[1].split(":")[0] < b.datumi.split(" ")[1].split(":")[0])
							return -1;	
						if(a.datumi.split(" ")[1].split(":")[1] > b.datumi.split(" ")[1].split(":")[1])
							return 1;
						if(a.datumi.split(" ")[1].split(":")[1] < b.datumi.split(" ")[1].split(":")[1])
							return -1;			
					}
					return 0;
				})
				this.sortiranoNaziv1 = 0
				this.sortiranoCena1 = 0
				this.sortiranoDatum1 = 1
			}
			else
			{
				this.personalniTreninziFilter.reverse()
				this.sortiranoDatum1 = 0
			}
		},
		
		sortNaziv2: function(){
			if(this.sortiranoNaziv2 === 0)
			{
				this.grupniTreninziFilter = this.grupniTreninziFilter.sort((a,b) => (a.treninzi.sportFacility > b.treninzi.sportFacility) ? 1 : ((a.treninzi.sportFacility < b.treninzi.sportFacility) ? -1 : 0));
				this.sortiranoNaziv2 = 1
				this.sortiranoCena2 = 0
				this.sortiranoDatum2 =0
			}
			else
			{
				this.grupniTreninziFilter.reverse()
				this.sortiranoNaziv1 = 0
			}
				
		},
		
		sortCena2: function(){
			if(this.sortiranoCena2 === 0)
			{
				this.grupniTreninziFilter = this.grupniTreninziFilter.sort((a,b) => (a.treninzi.price > b.treninzi.price) ? 1 : ((a.treninzi.price < b.treninzi.price) ? -1 : 0));
				this.sortiranoNaziv2 = 0
				this.sortiranoCena2 = 1
				this.sortiranoDatum2 = 0
			}
			else
			{
				this.grupniTreninziFilter.reverse()
				this.sortiranoCena2 = 0
			}
				
		},
		
		sortDatum2: function(){
			if(this.sortiranoDatum2 === 0)
			{
				this.grupniTreninziFilter = this.grupniTreninziFilter.sort((a,b) => {
					
					const pa = a.datumi.split(" ")[0].split(".");
					const pb = b.datumi.split(" ")[0].split(".");
					if(parseInt(pa[2]) > parseInt(pb[2]))
						return 1;
					else if(parseInt(pa[2]) < parseInt(pb[2]))
						return -1;
					else if (parseInt(pa[1]) > parseInt(pb[1]))
						return 1;
					else if(parseInt(pa[1]) < parseInt(pb[1]))
						return -1;
					else if (parseInt(pa[0]) > parseInt(pb[0]))
						return 1;
					else if(parseInt(pa[0]) < parseInt(pb[0]))
						return -1;
					else 
					{
						if(a.datumi.split(" ")[1].split(":")[0] > b.datumi.split(" ")[1].split(":")[0])
							return 1;
						if(a.datumi.split(" ")[1].split(":")[0] < b.datumi.split(" ")[1].split(":")[0])
							return -1;	
						if(a.datumi.split(" ")[1].split(":")[1] > b.datumi.split(" ")[1].split(":")[1])
							return 1;
						if(a.datumi.split(" ")[1].split(":")[1] < b.datumi.split(" ")[1].split(":")[1])
							return -1;		
					}
					return 0;
				})
				this.sortiranoNaziv2 = 0
				this.sortiranoCena2 = 0
				this.sortiranoDatum2 = 1
			}
			else
			{
				this.grupniTreninziFilter.reverse()
				this.sortiranoDatum2 = 0
			}
		},
		
		tipObjektaFilter1: function(evt){
			var t = evt.target.value;
			if(t == "Izaberi tip objekta")
			{
				this.tipo1 = '';
				this.personalniTreninziFilter = this.personalniTreninzi;
			}
			else
			{
				this.tipo1 = t;	
				this.personalniTreninziFilter = this.personalniTreninzi.filter(o => o.tipovi == this.tipo1);
			}
				
		},
		
		tipObjektaFilter2: function(evt){
			var t = evt.target.value;
			if(t == "Izaberi tip objekta")
			{
				this.tipo2 = '';
				this.grupniTreninziFilter = this.grupniTreninzi;
			}
			else
			{
				this.tipo2 = t;	
				this.grupniTreninziFilter = this.grupniTreninzi.filter(o => o.tipovi == this.tipo2);
			}
				
		},
	}
});