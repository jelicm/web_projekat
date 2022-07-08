var app = new Vue({
    el: '#customerTrainings',
    data:{
        sportFacilities: {},
        treninzi: {},
		treninziFiltrirano: {},
		datum: '',
		vreme: ''
    },
	mounted() {
		axios.get('rest/sportFacilities/')
		.then(response => {this.sportFacilities = response.data})
		axios.get('rest/sportFacilities/getAllTrainings')
		.then(response => {
			this.treninzi = response.data
			this.treninziFiltrirano = response.data
		})
	},
	
	methods: {
		objektiFilter: function(evt){
			var t = evt.target.value;
			if(t == 'Izaberite objekat')
				this.treninziFiltrirano = this.treninzi
			else
				this.treninziFiltrirano = this.treninzi.filter(tr => tr.sportFacility == t)
		},
		
		dodajTrening: function(t){
			if(!this.datum || !this.vreme){
				alert("Postoji nepopunjeno polje forme za datum ili vreme treninga!")
				return
			}
			var array = this.vreme.split(":")
			if(Number(array[0])<8 || Number(array[0])>21){
				alert("Neispravan vremenski interval!")
				return
			}
			var dateAndTime = this.datum + ' ' + this.vreme
			axios.post('rest/users/addTraining/' + dateAndTime, {"name": t.name, "trainingType": t.trainingType, "sportFacility": t.sportFacility,
			"durationInMinutes": t.durationInMinutes, "coach": t.coach, "description": t.description, "image": t.image, "price": t.price})
			.then(response => {
                location.href=response.data 
            })
            .catch(function(){
                alert("Članarina nije validna!")
            })

			

		},
		
	}
})