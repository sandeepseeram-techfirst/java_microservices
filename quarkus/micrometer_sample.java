import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import jakarta.ws.rs.PathParam; 

// This is the main Micrometer class to register custom metrics
    private final MeterRegistry registry;
    // The data to monitor 
    private AtomicInteger currentMemory;
    // Injects the Micrometer registry
    GreetingResource(MeterRegistry registry) {
        this.registry = registry;
        // Registers this metric under current.memory name, initializing the counter to 0
        currentMemory = this.registry.gauge("current.memory", Tags.empty(), new AtomicInteger(0));
    }
    // Creates an endpoint to modify the observed variable 
    @GET
    @Path("/consume/{amount}")
    @Produces(MediaType.TEXT_PLAIN)
    public Integer consume(@PathParam("amount") int mem) {
        this.currentMemory.addAndGet(mem);
        return this.currentMemory.get();
    }