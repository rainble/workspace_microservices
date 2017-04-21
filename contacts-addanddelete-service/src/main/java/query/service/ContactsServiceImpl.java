package query.service;

import query.domain.AddContactsInfo;
import query.domain.Contacts;
import query.repository.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class ContactsServiceImpl implements ContactsService{

    @Autowired
    private ContactsRepository contactsRepository;

    @Override
    public Contacts create(AddContactsInfo aci){

        Contacts contacts = new Contacts();
        contacts.setId(UUID.randomUUID());
        contacts.setName(aci.getName());
        contacts.setPhoneNumber(aci.getPhoneNumber());
        contacts.setDocumentNumber(aci.getDocumentNumber());
        contacts.setAccountId(aci.getAccountId());
        contacts.setDocumentType(aci.getDocumentType());

        ArrayList<Contacts> accountContacts = contactsRepository.findByAccountId(contacts.getAccountId());
        if(accountContacts.contains(contacts)){
            System.out.println("[Contacts-Add&Delete-Service][AddContacts] Fail.Contacts already exists");
            return null;
        }else{
            contactsRepository.save(contacts);
            System.out.println("[Contacts-Add&Delete-Service][AddContacts] Success.");
            return contacts;
        }
    }

    @Override
    public String delete(UUID contactsId){
        contactsRepository.deleteById(contactsId);
        Contacts contacts = contactsRepository.findById(contactsId);
        if(contacts == null){
            System.out.println("[Contacts-Add&Delete-Service][DeleteContacts] Success.");
            return "status=success";
        }else{
            System.out.println("[Contacts-Add&Delete-Service][DeleteContacts] Fail.Reason not clear.");
            return "status=fail";
        }
    }

}


