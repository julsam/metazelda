package net.bytten.metazelda;

public class Edge {

    protected Condition condition;
    
    public Edge() {
        condition = null;
    }
    
    public Edge(Condition condition) {
        this.condition = condition;
    }
    
    public boolean hasCondition() {
        return condition != null && condition.getSymbols().size() > 0;
    }
    
    public Condition getCondition() {
        return condition;
    }
    
    @Override
    public boolean equals(Object other) {
        if (other instanceof Edge) {
            Edge o = (Edge)other;
            return condition == o.condition || condition.equals(o.condition);
        } else {
            return super.equals(other);
        }
    }
    
}
