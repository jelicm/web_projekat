var app = new Vue({
    el: '#profileAdmin',
    data:{
        admin: null,
		username: ''
    },
	mounted() {
		axios.get('rest/users/loggedInAdmin')
		.then(response => {this.admin = response.data; this.username = response.data.username})
	},
    methods: {
		update: function(){
			axios.put('rest/users/updateAdmin/'+this.username, this.admin)
			.then(response => {
                location.href = response.data
            })
            .catch(function(){
                alert("Korisnicko ime nije jedinstveno!")
            })
		}
    }
})