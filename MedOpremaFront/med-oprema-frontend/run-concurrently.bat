@echo off
echo Running Newman for User 1...
start "" cmd /k "newman run "C:\Fakultet\ISA\git\MedicinskaOpremaIsa\MedOpremaFront\med-oprema-frontend\user1 concurrent testing.postman_collection.json" -e "C:\Fakultet\ISA\git\MedicinskaOpremaIsa\MedOpremaFront\med-oprema-frontend\user1 concurrent testing.postman_environment.json" --reporters cli & pause"

echo Running Newman for User 2...
start "" cmd /k "newman run "C:\Fakultet\ISA\git\MedicinskaOpremaIsa\MedOpremaFront\med-oprema-frontend\user2 concurrent testing.postman_collection.json" -e "C:\Fakultet\ISA\git\MedicinskaOpremaIsa\MedOpremaFront\med-oprema-frontend\user2 concurrent testing.postman_environment.json" --reporters cli & pause"
