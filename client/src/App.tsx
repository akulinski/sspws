import * as React from 'react';
import './App.css';

import logo from './logo.svg';


interface User {
    id: number;
    username: string;
    password: string;
    gender: string;
}

interface AppProps {
}

interface AppState {
    users: Array<User>;
    isLoading: boolean;
}


class App extends React.Component<AppProps, AppState> {

    constructor(props: AppProps) {
        super(props);
        this.state = {
            users: [],
            isLoading: false
        };
    }


    render() {
        const {users, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h1 className="App-title">Welcome to React</h1>
                </header>
                <div>
                    <h2>Users List</h2>
                    {users.map((user: User) =>
                        <div key={user.id}>
                            {user.username}
                        </div>
                    )}
                </div>
            </div>
        );
    }

    componentDidMount() {
        this.setState({isLoading: true});

        fetch('http://localhost:8080/api/users/getAllUsers')
            .then(response => response.json())
            .then(data => this.setState({users: data, isLoading: false}));
    }
}


export default App;
