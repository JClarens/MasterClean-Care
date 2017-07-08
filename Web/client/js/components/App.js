import React from 'react'
import { Route, Redirect } from 'react-router-dom'
import FilterBar from '../containers/FilterBar'
import AppDrawer from './AppDrawer'
import { simpleAuthentication } from '../containers/LoginContainer'
import NotificationContainer from '../containers/NotificationContainer'
import history from '../modules/history'

const App = () => {
    return (
        <div>
            <AppDrawer history={history}/>
            <NotificationContainer />
        </div>
    )
}

export default App