var app = new Vue({
    el: '#registeredUsers',
    data:{
		registrovaniKorisnici: {}
    },

	mounted() {
		axios
			.get("rest/users/registeredUsers")
			.then((response) => {
				this.registrovaniKorisnici = response.data;
			});
	}
})