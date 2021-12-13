export const getAuthHeader = () => {
    const user = localStorage.getItem("user");
  
    return { Authorization: "Bearer " + user };
}