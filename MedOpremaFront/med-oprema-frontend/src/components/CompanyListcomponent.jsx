import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const CompanyListComponent = () => {
    const [companies, setCompanies] = useState([]);

    useEffect(() => {
        axios.get('http://localhost:8080/api/companies')
            .then(response => {
                setCompanies(response.data);
            })
            .catch(error => {
                console.error('There was an error fetching the companies!', error);
            });
    }, []);

    return (
        <div className="container">
          <h2 className="mt-4 mb-3">Companies</h2>
          <ul className="list-group">
            {companies.map(company => (
              <li key={company.id} className="list-group-item">
                <Link to={`/companies/${company.id}/equipment`}>{company.companyName}</Link>
              </li>
            ))}
          </ul>
        </div>
      );
      
}

export default CompanyListComponent;
