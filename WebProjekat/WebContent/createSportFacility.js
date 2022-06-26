var app = new Vue({
    el: '#createSportFacility',
    data:{
        naziv: '',
		tip: '',
		geoDuzina: '',
		geoSirina: '',
		ulica: '',
		broj: '',
		grad: '',
		kod: '',
		slika: '',
		menadzer: '',
		menadzeri: {}
    },
    mounted(){
		axios.get('rest/users/availableManagers')
		.then(response => {this.menadzeri = response.data})
    },
    methods: {
        create: function(event){
            event.preventDefault()
            let array = this.slika.split("\\")
            this.slika = array[array.length - 1]
            if(!this.naziv || !this.tip || !this.geoDuzina || !this.geoSirina || !this.ulica || !this.broj || !this.grad
            	|| !this.kod || !this.slika){
				alert("Postoji nepopunjeno polje forme!")
				return
			}
			if(this.menadzer){
				axios.post('rest/sportFacilities/createSportFacility/' + this.menadzer, 
	            {"name": this.naziv, "type": this.tip, "trainings": null, "status": "NE_RADI",
	            "location": {"longitude": this.geoDuzina, "latitude": this.geoSirina, "address": {"number" : this.broj,
	            "street" : this.ulica, "city" : this.grad, "zipCode" : this.kod} }, "image" : this.slika, "averageRating" : 0.0,
	            "workTime" : "", "deleted": false})
	            .then(response => {
	                location.href=response.data 
	            })
	            .catch(function(){
	                alert("Ime objekta nije jedinstveno!")
	            })			
			}
			else{
				
			}
        }
    }
})