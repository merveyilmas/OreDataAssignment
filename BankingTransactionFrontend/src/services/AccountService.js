import axios from "axios"
import HostAndPortInfo from "../enums/HostAndPortInfo";

const ipAdress = HostAndPortInfo.HOSTNAME
const port = HostAndPortInfo.PORT

export default class AccountService {


    createAccount() {

        return axios({
            method: 'post',
            maxBodyLength: Infinity,
            url: `http://${ipAdress}:${port}/api/accounts`,
            headers: { 'Authorization': 'Bearer ' + sessionStorage.getItem('token') }
        })
    }

    searchAccount(nameOrNumber) {

        return axios({
            method: 'post',
            maxBodyLength: Infinity,
            url: `http://${ipAdress}:${port}/api/accounts/search?nameOrNumber=${nameOrNumber}`,
            headers: { 'Authorization': 'Bearer ' + sessionStorage.getItem('token') }
        })
    }

    updateAccount(accountId, updateDatas) {

        return axios({
            method: 'put',
            maxBodyLength: Infinity,
            url: `http://${ipAdress}:${port}/api/accounts/${accountId}`,
            data: updateDatas,
            headers: { 'Authorization': 'Bearer ' + sessionStorage.getItem('token') }
        })
    }

    
    deleteAccount(accountId) {

        return axios({
            method: 'delete',
            maxBodyLength: Infinity,
            url: `http://${ipAdress}:${port}/api/accounts/${accountId}`,
            headers: { 'Authorization': 'Bearer ' + sessionStorage.getItem('token') }
        })
    }

    getAccountById(accountId) {

        return axios({
            method: 'get',
            maxBodyLength: Infinity,
            url: `http://${ipAdress}:${port}/api/accounts/${accountId}`,
            headers: { 'Authorization': 'Bearer ' + sessionStorage.getItem('token') }
        })
    }
    
    getAccountByNumber(accountNumber) {

        return axios({
            method: 'get',
            maxBodyLength: Infinity,
            url: `http://${ipAdress}:${port}/api/accounts/with-number/${accountNumber}`,
            headers: { 'Authorization': 'Bearer ' + sessionStorage.getItem('token') }
        })
    }

    getAllAccountsByAuthUser() {

        return axios({
            method: 'get',
            maxBodyLength: Infinity,
            url: `http://${ipAdress}:${port}/api/accounts`,
            headers: { 'Authorization': 'Bearer ' + sessionStorage.getItem('token') }
        })
    }


}