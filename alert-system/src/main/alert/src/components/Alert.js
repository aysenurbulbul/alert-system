import React from 'react';
import {Icon, Table} from "semantic-ui-react";
import {Link} from "react-router-dom";
import axios from 'axios';

class Alert extends React.Component {

    handleDelete = (e) => {
        let url = 'http://localhost:8080/deleteAlert/' + e.target.id
        console.log(url)
        axios.delete(url).then((response) => {
            console.log(response)
            this.props.handler() //to refresh alert list
        })
            .catch((error) => {
                console.log(error.message);
            })
    }

    handleUpdate = (e) => {
        console.log(e.target.id)
    }

    render() {
        let alerts = this.props.alerts.map(alert => {
            return (<Table.Row key = {alert.id}>
                    <Table.Cell><div>
                        <Icon id = {alert.id} link name = 'delete' onClick = {this.handleDelete}/>
                        <Link to={{pathname:'/alerts/' + alert.name}}>{alert.name}</Link>
                    </div></Table.Cell>
                    <Table.Cell>
                        {alert.url}
                    </Table.Cell>
                    <Table.Cell>
                        {alert.method}
                    </Table.Cell>
                    <Table.Cell>
                        {alert.period}
                    </Table.Cell>
                    <Table.Cell collapsing>
                        <Link to={{pathname:'/edit/' + alert.name}}><Icon id = {alert.id} name = 'edit' onClick = {this.handleUpdate}/></Link>
                    </Table.Cell>
                    </Table.Row>
            )
        })
        return(
            <Table.Body>{alerts}</Table.Body>
        )
    }
}


export default Alert;