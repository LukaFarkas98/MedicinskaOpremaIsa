import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import EquipmentListComponent from './EquipmentListComponent';

const CompanyListComponent = () => {
    const [companies, setCompanies] = useState([]);
    const [selectedCompany, setSelectedCompany] = useState(null);

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
          <h2 className="my-4">Select a Company</h2>
          <div className="list-group">
              {companies.map((company) => (
                  <button
                      key={company.id}
                      className={`list-group-item list-group-item-action ${selectedCompany === company.id ? 'active' : ''}`}
                      onClick={() => setSelectedCompany(company.id)}
                  >
                      {company.companyName}
                  </button>
              ))}
          </div>

          {selectedCompany && <EquipmentListComponent companyId={selectedCompany} />}
      </div>
  );
};

      


export default CompanyListComponent;
