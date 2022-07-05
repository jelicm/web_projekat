var app = new Vue({
    el: '#createComment',
    data:{
        ocena: 1,
		komentar: ""
		
    },
    methods: {
		dodajKomentar: function(){
			
			axios.post("rest/users/addComment", {"name": "", "customer": "", "sportFacility": "", "content": this.komentar, "mark": this.ocena, "permission": "NA_CEKANJU"})
			.then(response => {
                location.href=response.data 
            })
		}
    }
})