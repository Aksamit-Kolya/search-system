import psycopg2

# Replace the placeholders with your actual database credentials
db_url = "postgresql://postgres:postgres@localhost:5433/search_system_db"

# Connect to the PostgreSQL database using the connection string
conn = psycopg2.connect(db_url)
# Create a cursor
cur = conn.cursor()

# Execute a query to retrieve your edge data
cur.execute("""SELECT document_id, related_document_id FROM document_document""")

# Fetch all rows
edges = cur.fetchall()

# Close the cursor and connection
cur.close()
conn.close()

# Process the edges to generate the desired format
graph = {}
for edge in edges:
    source, target = edge
    if source not in graph:
        graph[source] = []
    graph[source].append([target, 1])  # Assuming a weight of 1 for each edge

# Convert the graph dictionary to the desired format
result = []
for node, edges in graph.items():
    result.append([node, 0, edges])

# Print the result
for entry in result:
    print(entry)