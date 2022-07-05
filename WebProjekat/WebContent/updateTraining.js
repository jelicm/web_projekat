var app = new Vue({
    el: '#trainingUpdate',
    data:{
		trening: null,
		slika: '',
		prethodnaSlika: '',
		izabraniTrener: '',
		treneri: {},
		name: ''
    },
    mounted(){
		axios.get('rest/users/allCoaches')
		.then(response => {this.treneri = response.data,
		axios.get('rest/users/getTraining')
		.then(response => {
			this.trening = response.data;
			this.treneri.forEach(t => {
			    if(t.username == this.trening.coach)
					this.izabraniTrener = t.name + " " + t.surname;
			});
			this.prethodnaSlika = this.trening.image;
			this.name = this.trening.name;
		})})
    },
    methods: {
        update: function(event){
            event.preventDefault()
            let array = this.slika.split("\\")
            this.trening.image = array[array.length - 1]
            if(!this.trening.name || !this.trening.trainingType || !this.izabraniTrener || !this.slika){
				alert("Postoji nepopunjeno obavezno polje forme!")
				return
			}
			/*let trajanje = 0
			let opis = ""
			if(this.trajanje){
				trajanje = this.trajanje
			}
			if(this.opis){
				opis = this.opis
			}*/
			axios.put('rest/users/updateTraining/' + this.izabraniTrener + "/" + this.name, this.trening)
	            .then(response => {
	                location.href=response.data 
	            })
	            .catch(function(){
	                alert("Ime treninga nije jedinstveno!")
	            })	
        }
    }
})