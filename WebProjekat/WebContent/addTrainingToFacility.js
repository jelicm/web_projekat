var app = new Vue({
    el: '#trainingInfo',
    data:{
        naziv: '',
		tip: '',
		slika: '',
		izabraniTrener: '',
		treneri: {},
		trajanje: '',
		opis: '',
		cena: 0
    },
    mounted(){
		axios.get('rest/users/allCoaches')
		.then(response => {this.treneri = response.data})
    },
    methods: {
        create: function(event){
            event.preventDefault()
            let array = this.slika.split("\\")
            let slikaPutanja = array[array.length - 1]
            if(!this.naziv || !this.tip || !slikaPutanja || !this.izabraniTrener){
				alert("Postoji nepopunjeno obavezno polje forme!")
				return
			}
			let trajanje = 0
			let opis = ""
			let cena = 0
			if(this.trajanje){
				trajanje = this.trajanje
			}
			if(this.opis){
				opis = this.opis
			}
			if(this.cena){
				cena = this.cena
			}
			axios.post('rest/users/createTraining/' + this.izabraniTrener, 
	            {"name": this.naziv, "trainingType": this.tip, "sportFacility": null, "image" : slikaPutanja,
	            "durationInMinutes" : trajanje, "coach" : null, "description": opis, "price": cena})
	            .then(response => {
	                location.href=response.data 
	            })
	            .catch(function(){
	                alert("Ime treninga nije jedinstveno!")
	            })	
        }
    }
})