// Place your Spring DSL code here
import au.org.ala.userdetails.marshaller.*;
beans = {
    customObjectMarshallers(CustomObjectMarshallers){
        marshallers =[
                new UserPropertyMarshaller(),
                new UserMarshaller()
        ]
    }
}
